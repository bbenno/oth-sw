package de.othr.bib48218.chat.controller;

import de.othr.bib48218.chat.entity.*;
import de.othr.bib48218.chat.service.IFChatService;
import de.othr.bib48218.chat.service.IFUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.*;

@Controller
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    private IFChatService chatService;

    @Autowired
    private IFUserService userService;

    @RequestMapping("/{identifier}")
    public ModelAndView showChat(@PathVariable String identifier, Principal principal) {
        try {
            Long id = Long.parseLong(identifier);
            Optional<User> principal_user = userService.getUserByUsername(principal.getName());
            Optional<? extends Chat> chat = chatService.getChatById(id);
            if (chat.isPresent() && chat.get().getMemberships().stream().anyMatch(m -> m.getUser().equals(principal_user.get()))) {
                return new ModelAndView("chat/show", "chat", chat.get());
            } else
                return new ModelAndView("redirect:/");
        } catch (NumberFormatException e) {
            // Interpret id as username
            Optional<User> user = userService.getUserByUsername(principal.getName());
            Optional<User> other = userService.getUserByUsername(identifier);
            PeerChat chat = chatService.getOrCreatePeerChatOf(user.get(), other.get());

            return new ModelAndView("chat/show", "chat", chat);
        } catch (Exception e) {
            return new ModelAndView("redirect:/");
        }
    }

    @RequestMapping("/{id}/add")
    public ModelAndView addChatMember(@PathVariable long id, Principal principal) {
        Chat chat = chatService.getChatById(id).get();

        return new ModelAndView("chat/add_member", "chat", chat);
    }

    @PostMapping("/{id}/add")
    public ModelAndView addChatMember(@PathVariable Long id, @RequestParam String username) {
        Optional<? extends Chat> chat = chatService.getChatById(id);
        Optional<User> user = userService.getUserByUsername(username);
        chatService.addUserToChat(user.get(), chat.get());

        return redirectToChat(chat.get());
    }

    @RequestMapping("/{id}/join")
    public ModelAndView joinChat(@PathVariable Long id, Principal principal) {
        Optional<User> user = userService.getUserByUsername(principal.getName());
        Optional<? extends Chat> chat = chatService.getChatById(id);
        chatService.addUserToChat(user.get(), chat.get());

        return redirectToChat(chat.get());
    }

    @RequestMapping("/{id}/delete")
    public ModelAndView deleteChat(@PathVariable Long id, Principal principal) {
        String message;
        Optional<? extends Chat> chat_opt = chatService.getChatById(id);

        if (chat_opt.isPresent()) {
            Chat chat = chat_opt.get();
            User user = userOfPrincipal(principal);

            if (userIsAllowedToDeleteChat(user, chat)) {
                chatService.deleteChat(chat);
                message = "Deleted chat successfully";
            } else {
                message = "No permission to delete chat";
            }
        }else {
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
    public ModelAndView createGroupChat(@Validated GroupChat chat, BindingResult bindingResult, Principal principal) {
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
        Optional<ChatMembership> membership = chat.getMemberships().stream().filter(m -> m.getUser() == user).findFirst();
        List<ChatMemberStatus> allowedStatus = Arrays.asList(ChatMemberStatus.ADMINISTRATOR);
        return membership.isPresent() && allowedStatus.contains(membership.get().getStatus());
    }
}
