package de.othr.bib48218.chat.controller;

import de.othr.bib48218.chat.entity.Chat;
import de.othr.bib48218.chat.service.IFChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    private IFChatService chatService;

    @RequestMapping("/{id}")
    public ModelAndView showChat(@PathVariable Long id) {
        Chat chat = chatService.getChatById(id);
        return new ModelAndView("chat/show", "chat", chat);
    }
}
