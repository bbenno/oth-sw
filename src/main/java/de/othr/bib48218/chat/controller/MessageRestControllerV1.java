package de.othr.bib48218.chat.controller;

import de.othr.bib48218.chat.entity.Chat;
import de.othr.bib48218.chat.entity.Message;
import de.othr.bib48218.chat.service.IFChatService;
import de.othr.bib48218.chat.service.IFMessageService;
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

    @Override
    @GetMapping("messages")
    public ResponseEntity<Collection<Message>> getMessages(
        @PathParam("chat") Long chatId,
        @PathParam("from") String dateTime
    ) {
        Optional<LocalDateTime> timestamp = Optional.empty();
        Optional<? extends Chat> chat = Optional.empty();

        // Parse DateTime
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
            if (timestamp.isPresent())
                return ResponseEntity.ok(messageService.getMessagesByChatFrom(chat.get(), timestamp.get()));
            else
                return ResponseEntity.ok(messageService.getAllMessagesByChat(chat.get()));
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
