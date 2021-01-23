package de.othr.bib48218.chat.controller;

import de.othr.bib48218.chat.entity.Chat;
import de.othr.bib48218.chat.entity.ChatMemberStatus;
import de.othr.bib48218.chat.entity.ChatMembership;
import de.othr.bib48218.chat.entity.GroupChat;
import de.othr.bib48218.chat.entity.User;
import de.othr.bib48218.chat.service.IFChatService;
import de.othr.bib48218.chat.service.IFUserService;
import java.security.Principal;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/chat")
public class ChatController {

    private final Pattern pattern = Pattern.compile("\\d+");

    @Autowired
    private IFChatService chatService;

    @Autowired
    private IFUserService userService;

    @RequestMapping("/{identifier}")
    public ModelAndView showChat(
        @PathVariable String identifier,
        Principal principal,
        Model model
    ) {
        User user = userOfPrincipal(principal);
        Optional<Chat> chat =
            (pattern.matcher(identifier).matches())
                // Interpret identifier as chat id
                ? chatService.getChatById(Long.parseLong(identifier))
                // Interpret identifier as username
                : userService.getUserByUsername(identifier)
                    .map(u -> chatService.getOrCreatePeerChatOf(user, u));

        chat.ifPresentOrElse(
            c -> model.addAllAttributes(Map.of(
                "chat",
                c,

                "isAdmin",
                c.getStatusOfMember(user)
                    .map(s -> s == ChatMemberStatus.ADMINISTRATOR)
                    .orElse(false)
            )),
            () -> model.addAttribute("notification", "Chat not found")
        );

        String view = chat.map(c -> c.getStatusOfMember(user).isPresent()).orElse(false)
            ? "chat/show"
            : "redirect:/";

        return new ModelAndView(view, model.asMap());
    }

    @RequestMapping("/{id}/add")
    public ModelAndView addChatMember(@PathVariable long id, Principal principal, Model model) {
        Optional<Chat> chat = chatService.getChatById(id);

        chat.ifPresent(c -> model.addAttribute("chat", c));

        String view =
            (chat.map(c -> isAllowedToAddMemberToChat(principal, c)).orElse(false))
                ? "chat/add_member"
                : "redirect:/";

        return new ModelAndView(view, model.asMap());
    }

    @PostMapping("/{id}/add")
    @Transactional
    public ModelAndView addChatMember(
        @PathVariable Long id,
        @RequestParam String username,
        Principal principal,
        Model model
    ) {
        Optional<User> newMember = userService.getUserByUsername(username);
        Optional<Chat> chat = chatService.getChatById(id);

        chat.ifPresentOrElse(
            c -> model.addAttribute("chat", c),
            () -> model.addAttribute("notification", "Chat not found")
        );

        boolean isUserAdded = newMember.isPresent()
            && chat.map(c -> isAllowedToAddMemberToChat(principal, c)).orElse(false);

        if (isUserAdded) {
            addUserToChat(newMember.get(), chat.get());
        }

        String view =
            (newMember.isEmpty())
                ? "chat/add_member"
                : (isUserAdded)
                    ? "redirect:"
                    : "redirect:/";

        return new ModelAndView(view, model.asMap());
    }

    @RequestMapping("/{id}/join")
    @Transactional
    public ModelAndView joinChat(@PathVariable Long id, Principal principal) {
        return chatService.getChatById(id)
            .map(c -> {
                addUserToChat(userOfPrincipal(principal), c);
                return redirectToChat(c);
            })
            .orElseGet(this::redirectToHome);
    }

    @RequestMapping("/{id}/leave")
    @Transactional
    public ModelAndView leaveChat(@PathVariable Long id, Principal principal) {
        chatService.getChatById(id).ifPresent(
            chat -> chat.getMemberships()
                .removeIf(m -> m.getUser().equals(userOfPrincipal(principal)))
        );

        return new ModelAndView("redirect:/");
    }

    @RequestMapping("/{id}/edit")
    public ModelAndView editChat(@PathVariable Long id, Principal principal) {
        return chatService.getGroupChatById(id)
            .map(groupChat -> new ModelAndView("chat/edit_group_chat", "chat", groupChat))
            .orElseGet(this::redirectChatNotFound);
    }

    @PostMapping("/{id}/edit")
    public ModelAndView saveEditedChat(
        @Validated GroupChat chat,
        @PathVariable Long id,
        BindingResult bindingResult,
        Principal principal
    ) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("chat/" + id + "/edit", "chat", chat);
        }

        chatService.getGroupChatById(id).ifPresent(c -> {
            c.setVisibility(chat.getVisibility());
            c.getProfile().setDescription(chat.getProfile().getDescription());
            c.getProfile().setName(chat.getProfile().getName());
            c.getProfile().setImagePath(chat.getProfile().getImagePath());
        });

        return new ModelAndView("redirect:/chat/" + id);

    }

    @RequestMapping("/{id}/delete")
    public ModelAndView deleteChat(@PathVariable Long id, Principal principal) {
        return redirectToHome(
            chatService.getChatById(id)
                .map(c -> {
                    if (c instanceof GroupChat && isAllowedToDeleteChat(principal, c)
                    ) {
                        chatService.deleteChat(c);
                        return "Deleted chat successully";
                    } else {
                        return "No permission to delete chat";
                    }
                })
                .orElse("Error deleting chat")
        );
    }

    @RequestMapping("/public")
    public ModelAndView publicChats(Principal principal) {
        Collection<GroupChat> chats = chatService.getAllPublicGroupChats().stream()
            .filter(
                c -> c.getMemberships().stream()
                    .noneMatch(m -> m.getUser().equals(userOfPrincipal(principal)))
            ).collect(Collectors.toUnmodifiableList());

        return new ModelAndView("chat/join_public", "chats", chats);
    }

    @RequestMapping("/new")
    public ModelAndView showGroupChatForm() {
        return new ModelAndView("chat/new_group_chat", "chat", new GroupChat());
    }

    @PostMapping("/new")
    public ModelAndView createGroupChat(
        @Validated GroupChat chat,
        BindingResult bindingResult,
        Principal principal
    ) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("chat/new_group_chat", "chat", chat);
        }

        return redirectToChat(chatService.saveChat(userOfPrincipal(principal), chat));
    }

    private ModelAndView redirectToChat(@Nullable Chat chat) {
        return new ModelAndView("redirect:/chat/" + ((chat == null) ? "" : chat.getId()));
    }

    private ModelAndView redirectChatNotFound() {
        return new ModelAndView("redirect:/", "notification", "Chat not found");
    }

    private ModelAndView redirectToHome() {
        return new ModelAndView("redirect:/");
    }

    private ModelAndView redirectToHome(String notification) {
        return new ModelAndView("redirect:/", "notification", notification);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    protected void addUserToChat(User user, Chat chat) {
        addOrUpdateChatMembership(user, chat, ChatMemberStatus.MEMBER);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    protected void addOrUpdateChatMembership(User user, Chat chat, ChatMemberStatus status) {
        chat.getMemberships().stream()
            .filter(m -> m.getUser().equals(user) && m.getChat().equals(chat))
            .findAny()
            .ifPresentOrElse(
                m -> m.setStatus(status),
                () -> chat.getMemberships().add(new ChatMembership(chat, status, user))
            );
    }

    private User userOfPrincipal(Principal principal) {
        return userService.getUserByUsername(principal.getName()).orElseThrow();
    }

    private boolean isAllowedToDeleteChat(Principal principal, Chat chat) {
        return chat.getStatusOfMember(userOfPrincipal(principal))
            .map(ChatMemberStatus::isAllowedToDeleteChat)
            .orElse(false);
    }

    private boolean isAllowedToEditChat(Principal principal, Chat chat) {
        return chat.getStatusOfMember(userOfPrincipal(principal))
            .map(ChatMemberStatus::isAllowedToEditChat)
            .orElse(false);
    }

    private boolean isAllowedToAddMemberToChat(Principal principal, Chat chat) {
        return chat.getStatusOfMember(userOfPrincipal(principal))
            .map(ChatMemberStatus::isAllowedToAddMembersToChat)
            .orElse(false);
    }
}
