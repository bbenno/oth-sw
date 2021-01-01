package de.othr.bib48218.chat.controller;

import de.othr.bib48218.chat.entity.Chat;
import de.othr.bib48218.chat.entity.GroupChat;
import de.othr.bib48218.chat.entity.User;
import de.othr.bib48218.chat.service.IFChatService;
import de.othr.bib48218.chat.service.IFUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    private IFChatService chatService;

    @Autowired
    private IFUserService userService;

    @RequestMapping("/{id}")
    public ModelAndView showChat(@PathVariable Long id) {
        Chat chat = chatService.getChatById(id);
        return new ModelAndView("/chat/show", "chat", chat);
    }

    @RequestMapping("/{id}/add")
    public ModelAndView addChatMember(@PathVariable long id, Principal principal) {
        Chat chat = chatService.getChatById(id);
        return new ModelAndView("chat/add_member", "chat", chat);
    }

    @PostMapping("/{id}/add")
    public ModelAndView addChatMember(@PathVariable Long id, @RequestParam String username) {
        Chat chat = chatService.getChatById(id);
        User user = userService.getUserByUsername(username);
        chatService.addUserToChat(user, chat);
        return new ModelAndView("redirect:/chat/" + chat.getId());
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
            User creator = userService.getUserByUsername(principal.getName());
            chat = chatService.createGroupChat(creator, chat);
            return new ModelAndView("redirect:/chat/" + chat.getId());
        }
    }
}
