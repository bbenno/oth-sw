package de.othr.bib48218.chat.controller;

import de.othr.bib48218.chat.entity.Chat;
import de.othr.bib48218.chat.entity.ChatMemberStatus;
import de.othr.bib48218.chat.entity.GroupChat;
import de.othr.bib48218.chat.entity.User;
import de.othr.bib48218.chat.service.IFChatService;
import de.othr.bib48218.chat.service.IFUserService;
import java.security.Principal;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
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

    @Autowired
    private IFChatService chatService;

    @Autowired
    private IFUserService userService;

    @RequestMapping("/{identifier}")
    public ModelAndView showChat(@PathVariable String identifier, Principal principal,
        Model model) {
        Chat chat;
        try {
            Long id = Long.parseLong(identifier);
            Optional<User> principal_user = userService.getUserByUsername(principal.getName());
            Optional<? extends Chat> chat_opt = chatService.getChatById(id);
            if (chat_opt.isEmpty() || chat_opt.get().getMemberships().stream()
                .noneMatch(m -> m.getUser().equals(principal_user.get()))) {
                return new ModelAndView("redirect:/");
            } else {
                chat = chat_opt.get();
            }
        } catch (NumberFormatException e) {
            // Interpret id as username
            Optional<User> user = userService.getUserByUsername(principal.getName());
            Optional<User> other = userService.getUserByUsername(identifier);
            chat = chatService.getOrCreatePeerChatOf(user.get(), other.get());
        } catch (Exception e) {
            return new ModelAndView("redirect:/", "notification", "Chat not found");
        }

        model.addAttribute("chat", chat);
        boolean isAdmin = chat.getClass().equals(GroupChat.class);
        model.addAttribute("isGroupChat", isAdmin);
        if (isAdmin) {
            model.addAttribute("isAdmin", hasUserMemberStatus(userOfPrincipal(principal), chat,
                ChatMemberStatus.ADMINISTRATOR));
        }

        return new ModelAndView("chat/show", model.asMap());
    }

    @RequestMapping("/{id}/add")
    public ModelAndView addChatMember(@PathVariable long id, Principal principal) {
        Chat chat = chatService.getChatById(id).get();

        if (!userIsAllowedToAddMember(userOfPrincipal(principal), chat)) {
            return new ModelAndView("redirect:/");
        }
        return new ModelAndView("chat/add_member", "chat", chat);
    }

    @PostMapping("/{id}/add")
    public ModelAndView addChatMember(@PathVariable Long id, @RequestParam String username,
        Principal principal) {
        Optional<? extends Chat> chat = chatService.getChatById(id);
        if (chat.isEmpty()) {
            return new ModelAndView("redirect:/", "notification", "Chat not found");
        }

        Optional<User> user = userService.getUserByUsername(username);
        if (user.isEmpty()) {
            return new ModelAndView("chat/add_member", "chat", chat.get());
        }

        if (userIsAllowedToAddMember(userOfPrincipal(principal), chat.get())) {
            chatService.addUserToChat(user.get(), chat.get());
        }
        return redirectToChat(chat.get());
    }

    @RequestMapping("/{id}/join")
    public ModelAndView joinChat(@PathVariable Long id, Principal principal) {
        Optional<User> user = userService.getUserByUsername(principal.getName());
        Optional<? extends Chat> chat = chatService.getChatById(id);
        chatService.addUserToChat(user.get(), chat.get());

        return redirectToChat(chat.get());
    }

    @RequestMapping("/{id}/leave")
    public ModelAndView leaveChat(@PathVariable Long id, Principal principal) {
        chatService.deleteChatMembership(userOfPrincipal(principal), id);

        return new ModelAndView("redirect:/");
    }

    @RequestMapping("/{id}/edit")
    public ModelAndView editChat(@PathVariable Long id, Principal principal) {
        return chatService.getGroupChatById(id)
            .map(groupChat -> new ModelAndView("chat/edit_group_chat", "chat", groupChat))
            .orElseGet(() -> new ModelAndView("redirect:/", "notification", "Chat not found"));
    }

    @PostMapping("/edited")
    public ModelAndView saveEditedChat(@Validated GroupChat chat, Principal principal) {
        chatService.editGroupChat(chat);
        return new ModelAndView("redirect:/chat/" + chat.getId());
    }

    @RequestMapping("/{id}/delete")
    public ModelAndView deleteChat(@PathVariable Long id, Principal principal) {
        String message;
        Optional<? extends Chat> chat_opt = chatService.getChatById(id);

        if (chat_opt.isPresent()) {
            Chat chat = chat_opt.get();
            User user = userOfPrincipal(principal);

            if (chat.getClass().equals(GroupChat.class) && userIsAllowedToDeleteChat(user, chat)) {
                chatService.deleteChat(chat);
                message = "Deleted chat successfully";
            } else {
                message = "No permission to delete chat";
            }
        } else {
            message = "Error deleting chat";
        }
        return new ModelAndView("redirect:/", "notification", message);
    }

    @RequestMapping("/public")
    public ModelAndView publicChats() {
        Collection<GroupChat> chats = chatService.getAllPublicGroupChats();

        return new ModelAndView("chat/join_public", "chats", chats);
    }

    @RequestMapping("/new")
    public ModelAndView showGroupChatForm() {
        var chat = new GroupChat();
        return new ModelAndView("chat/form_groupChat", "chat", chat);
    }

    @PostMapping("/new")
    public ModelAndView createGroupChat(@Validated GroupChat chat, BindingResult bindingResult,
        Principal principal) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("chat/form_groupChat", "chat", chat);
        } else {
            Optional<User> creator = userService.getUserByUsername(principal.getName());
            chat = chatService.saveChat(creator.get(), chat);

            return redirectToChat(chat);
        }
    }

    private ModelAndView redirectToChat(@Nullable Chat chat) {
        return new ModelAndView("redirect:/chat/" + ((chat == null) ? "" : chat.getId()));
    }

    private User userOfPrincipal(Principal principal) {
        return userService.getUserByUsername(principal.getName()).orElseThrow();
    }

    private boolean userIsAllowedToDeleteChat(User user, Chat chat) {
        return hasUserMemberStatus(user, chat, Arrays.asList(ChatMemberStatus.ADMINISTRATOR));
    }

    private boolean userIsAllowedToAddMember(User user, Chat chat) {
        return hasUserMemberStatus(user, chat, Arrays.asList(ChatMemberStatus.ADMINISTRATOR));
    }

    private boolean hasUserMemberStatus(User user, Chat chat, List<ChatMemberStatus> status) {
        return chat.getMemberships().stream().filter(m -> m.getUser() == user)
            .anyMatch(m -> status.contains(m.getStatus()));
    }

    private boolean hasUserMemberStatus(User user, Chat chat, ChatMemberStatus status) {
        return chat.getMemberships().stream().filter(m -> m.getUser() == user)
            .anyMatch(m -> m.getStatus() == status);
    }
}
