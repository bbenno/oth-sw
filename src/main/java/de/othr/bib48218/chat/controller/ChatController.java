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

/**
 * The controller for {@link Chat}.
 */
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Controller
@RequestMapping("/chat")
public class ChatController {

    private static final Pattern NUMBER_PATTERN = Pattern.compile("\\d+");

    /**
     * Default notification message if chat is not found.
     */
    private static final String CHAT_NOT_FOUND = "Chat not found";

    @Autowired
    private IFChatService chatService;

    @Autowired
    private IFUserService userService;

    @RequestMapping("/{identifier}")
    public String showChat(
        @PathVariable String identifier,
        Principal principal,
        Model model
    ) {
        User user = userOfPrincipal(principal);

        if (NUMBER_PATTERN.matcher(identifier).matches()) {
            // Interpret identifier as chat id
            return showGroupChat(Long.parseLong(identifier), user, model);
        } else {
            // Interpret identifier as username
            return showPeerChat(identifier, user, model);
        }
    }

    @RequestMapping("/{id}/add")
    public String addChatMember(@PathVariable long id, Principal principal, Model model) {
        return chatService.getChatById(id).map(chat -> {
            if (isAllowedToAddMemberToChat(principal, chat)) {
                model.addAttribute("chat", chat);
                return "chat/add_member";
            } else {
                return "redirect:/";
            }
        }).orElse("redirect:/");
    }

    @PostMapping("/{id}/add")
    @Transactional
    public String addChatMember(
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

        model.addAttribute("isUsernameTaken", !isUserAdded);

        return (newMember.isEmpty())
            ? "chat/add_member"
            : (isUserAdded)
                ? "redirect:"
                : "redirect:/";
    }

    @RequestMapping("/{id}/join")
    @Transactional
    public String joinChat(@PathVariable Long id, Principal principal, Model model) {
        return chatService.getChatById(id).map(chat -> {
            addUserToChat(userOfPrincipal(principal), chat);
            return "redirect:";
        }).orElseGet(
            () -> {
                model.addAttribute("notification", CHAT_NOT_FOUND);
                return "redirect:/";
            }
        );
    }

    @RequestMapping("/{id}/leave")
    @Transactional
    public String leaveChat(@PathVariable Long id, Principal principal) {
        chatService.getChatById(id).ifPresent(
            chat -> chat.getMemberships()
                .removeIf(m -> m.getUser().equals(userOfPrincipal(principal)))
        );

        return "redirect:/";
    }

    @RequestMapping("/{id}/edit")
    public String editChat(@PathVariable Long id, Principal principal, Model model) {
        Optional<GroupChat> chat = chatService.getGroupChatById(id);
        if (chat.isPresent()) {
            if (isAllowedToEditChat(principal, chat.get())) {
                model.addAttribute("chat", chat.get());
                return "chat/edit_group_chat";
            } else {
                return "redirect:";
            }
        } else {
            model.addAttribute("notification", CHAT_NOT_FOUND);
            return "redirect:/";
        }
    }

    @PostMapping("/{id}/edit")
    @Transactional
    public String saveEditedChat(
        @Validated GroupChat chat,
        @PathVariable Long id,
        BindingResult bindingResult,
        Principal principal,
        Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("chat", chat);
            return "redirect:edit";
        }

        chatService.getGroupChatById(id).ifPresent(c -> {
            if (isAllowedToEditChat(principal, c)) {
                c.setVisibility(chat.getVisibility());
                c.getProfile().setDescription(chat.getProfile().getDescription());
                c.getProfile().setName(chat.getProfile().getName());
                c.getProfile().setImagePath(chat.getProfile().getImagePath());
            }
        });

        return "redirect:";

    }

    @RequestMapping("/{id}/delete")
    @Transactional
    public String deleteChat(@PathVariable Long id, Principal principal, Model model) {
        String notification = chatService.getChatById(id)
            .map(c -> {
                if (c instanceof GroupChat && isAllowedToDeleteChat(principal, c)
                ) {
                    chatService.deleteChat(c);
                    return "Deleted chat successfully";
                } else {
                    return "No permission to delete chat";
                }
            })
            .orElse("Error deleting chat");

        model.addAttribute("notification", notification);
        return "redirect:/";
    }

    @RequestMapping("/public")
    public String publicChats(Principal principal, Model model) {
        Collection<GroupChat> chats = chatService.getAllPublicGroupChats().stream()
            // Filter all public chats in which the current user is already member in.
            .filter(
                c -> c.getMemberships().stream()
                    .noneMatch(m -> m.getUser().equals(userOfPrincipal(principal)))
            ).collect(Collectors.toUnmodifiableList());

        model.addAttribute("chats", chats);
        return "chat/join_public";
    }

    @RequestMapping("/new")
    public String showGroupChatForm(Model model) {
        model.addAttribute("chat", new GroupChat());
        return "chat/new_group_chat";
    }

    @PostMapping("/new")
    public String createGroupChat(
        @Validated GroupChat chat,
        BindingResult bindingResult,
        Principal principal,
        Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("chat", chat);
            return "chat/new_group_chat";
        }

        chat = chatService.saveChat(userOfPrincipal(principal), chat);
        return "redirect:" + chat.getId();
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

    private String showPeerChat(String identifier, User user, Model model) {
        return userService.getUserByUsername(identifier)
            .map(u -> chatService.getOrCreatePeerChatOf(user, u))
            .map(chat -> {
                model.addAttribute("chat", chat);
                return "chat/show";
            }).orElseGet(
                () -> {
                    model.addAttribute("notification", CHAT_NOT_FOUND);
                    return "redirect:/";
                }
            );
    }

    private String showGroupChat(Long chatId, User user, Model model) {
        Optional<Chat> chat = chatService.getChatById(chatId);
        if (chat.isPresent() && chat.get().getStatusOfMember(user).isPresent()) {
            model.addAllAttributes(Map.of(
                "chat",
                chat.get(),

                "isAdmin",
                chat.get().getStatusOfMember(user)
                    .map(s -> s == ChatMemberStatus.ADMINISTRATOR)
                    .orElse(false)
            ));
            return "chat/show";
        } else {
            model.addAttribute("notification", CHAT_NOT_FOUND);
            return "redirect:/";
        }
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
