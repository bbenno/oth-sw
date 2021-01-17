package de.othr.bib48218.chat.controller;

import de.othr.bib48218.chat.entity.Chat;
import de.othr.bib48218.chat.entity.Message;
import de.othr.bib48218.chat.entity.User;
import de.othr.bib48218.chat.service.IFChatService;
import de.othr.bib48218.chat.service.IFMessageService;
import de.othr.bib48218.chat.service.IFUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolationException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private IFMessageService messageService;

    @Autowired
    private IFChatService chatService;

    @Autowired
    private IFUserService userService;

    @RequestMapping("/new")
    private ModelAndView createMessage(@RequestParam("message") String text, @RequestParam("chat") Long chat_id, Principal principal) {
        Optional<User> author = userService.getUserByUsername(principal.getName());
        Optional<? extends Chat> chat_opt = chatService.getChatById(chat_id);
        if (chat_opt.isEmpty() || !chatService.isUserMember(author.get(), chat_opt.get()))
            return new ModelAndView("redirect:/", "notification", "chat not found");

        Chat chat = chat_opt.get();
        try {
            Message message = new Message(text, chat, author.get(), LocalDateTime.now());
            messageService.saveMessage(message);
        } catch (ConstraintViolationException ignored) {
        }
        return new ModelAndView("redirect:/chat/" + chat.getId());
    }
}
