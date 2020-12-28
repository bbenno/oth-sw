package de.othr.bib48218.chat.controller;

import de.othr.bib48218.chat.entity.Chat;
import de.othr.bib48218.chat.entity.Message;
import de.othr.bib48218.chat.entity.User;
import de.othr.bib48218.chat.service.ChatService;
import de.othr.bib48218.chat.service.MessageService;
import de.othr.bib48218.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @Autowired
    private ChatService chatService;

    @Autowired
    private UserService userService;

    @RequestMapping("/new")
    private ModelAndView createMessage(@RequestParam("message") String text, @RequestParam("chat") Long chat_id, Principal principal) {
        User author = userService.getUserByUsername(principal.getName());
        Chat chat = chatService.getChatById(chat_id);
        Message message = new Message(text, chat, author, LocalDateTime.now());
        messageService.saveMessage(message);
        return new ModelAndView("redirect:/");
    }
}
