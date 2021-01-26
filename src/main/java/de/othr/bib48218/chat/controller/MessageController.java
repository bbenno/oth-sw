package de.othr.bib48218.chat.controller;

import de.othr.bib48218.chat.entity.Chat;
import de.othr.bib48218.chat.entity.Message;
import de.othr.bib48218.chat.entity.User;
import de.othr.bib48218.chat.service.IFChatService;
import de.othr.bib48218.chat.service.IFMessageService;
import de.othr.bib48218.chat.service.IFUserService;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * The controller for {@link Message}.
 */
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
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
    private String createMessage(
        @RequestParam("message") String text,
        @RequestParam("chat") Long chat_id,
        Principal principal,
        Model model
    ) {
        Optional<Chat> chat = chatService.getChatById(chat_id);
        User author = userOfPrincipal(principal);

        if (chat.isPresent() && chat.get().getStatusOfMember(author).isPresent()) {
            try {
                Message message = new Message(text, chat.get(), author, LocalDateTime.now());
                messageService.saveMessage(message);
            } catch (ConstraintViolationException ignored) {
            }
            return "redirect:/chat/" + chat.get().getId();
        } else {
            model.addAttribute("notification", "Chat not found");
            return "redirect:/";
        }
    }

    private User userOfPrincipal(Principal principal) {
        return userService.getUserByUsername(principal.getName()).orElseThrow();
    }
}
