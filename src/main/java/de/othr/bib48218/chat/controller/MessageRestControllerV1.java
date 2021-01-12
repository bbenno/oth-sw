package de.othr.bib48218.chat.controller;

import de.othr.bib48218.chat.entity.Chat;
import de.othr.bib48218.chat.entity.Message;
import de.othr.bib48218.chat.service.IFChatService;
import de.othr.bib48218.chat.service.IFMessageService;
import de.othr.bib48218.chat.service.IFUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/webapi/v1/")
public class MessageRestControllerV1 implements IFMessageRestControllerV1 {

    @Autowired
    private IFMessageService messageService;

    @Autowired
    private IFChatService chatService;

    @Autowired
    private IFUserService userService;

    @Override
    @GetMapping("messages")
    public ResponseEntity<Collection<Message>> getMessages(
        @PathParam("chat") Long chatId,
        @PathParam("since") String dateTime,
        @PathParam("from") String username
    ) {
        Optional<LocalDateTime> timestamp = Optional.empty();
        Optional<? extends Chat> chat = Optional.empty();
        Optional<String> author_name = Optional.ofNullable(username);

        // Parse DateTime if present
        if (dateTime != null && !dateTime.isBlank()) {
            try {
                timestamp = Optional.of(LocalDateTime.parse(dateTime));
            } catch (DateTimeParseException e) {
            }
        }

        // Fetch chat if present
        if (chatId != null && chatId != 0) {
            chat = chatService.getChatById(chatId);
            if (chat.isEmpty())
                return ResponseEntity.noContent().build();
        }

        // Return Response
        if (chat.isPresent()) {
            if (author_name.isPresent()) {
                if (timestamp.isPresent()) {
                    return ResponseEntity.ok(messageService.getMessagesByChatSinceFrom(chat.get(), timestamp.get(), author_name.get()));
                } else {
                    return ResponseEntity.ok(messageService.getAllMessagesByChatFrom(chat.get(), author_name.get()));
                }
            } else {
                if (timestamp.isPresent()) {
                    return ResponseEntity.ok(messageService.getMessagesByChatSince(chat.get(), timestamp.get()));
                } else {
                    return ResponseEntity.ok(messageService.getAllMessagesByChat(chat.get()));
                }
            }
        }

        // Default Return
        return ResponseEntity.noContent().build();
    }

    @Override
    @PostMapping("messages")
    public ResponseEntity<Message> postMessage(Message message) {
        return ResponseEntity.of(Optional.ofNullable(messageService.saveMessage(message)));
    }
}
