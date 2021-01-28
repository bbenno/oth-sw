package de.othr.bib48218.chat.rest;

import de.othr.bib48218.chat.entity.Bot;
import de.othr.bib48218.chat.entity.Chat;
import de.othr.bib48218.chat.entity.Message;
import de.othr.bib48218.chat.entity.User;
import de.othr.bib48218.chat.service.IFChatService;
import de.othr.bib48218.chat.service.IFMessageService;
import de.othr.bib48218.chat.service.IFUserService;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import javax.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@RestController
@RequestMapping("/webapi/v1/messages")
class MessageRestControllerV1 implements IFMessageRestControllerV1 {

    @Autowired
    private IFMessageService messageService;

    @Autowired
    private IFChatService chatService;

    @Autowired
    @Qualifier("bankUserService")
    private IFUserService userService;

    /* CREATE  ************************************************************************************/

    @Override
    @Transactional
    @PostMapping
    public ResponseEntity<Boolean> postMessage(@RequestBody Message message) {
        if (message.getChat() == null || message.getAuthor() == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<? extends Chat> chat;
        Optional<? extends User> author;

        Bot bot = userService.getBotByUsername("bank_service").orElse(null);

        // If service bot not found
        if (bot == null) {
            return ResponseEntity.ok(false);
        }

        if (message.getAuthor().getUsername().equals("bank_service")) {
            // If service bot sends message to user

            var chatId = message.getChat().getId();
            if (chatId == null) {
                return ResponseEntity.ok(false);
            }

            chat = chatService.getChatById(chatId);
            author = Optional.of(bot);
        } else {
            // If user sends message to service bot

            author = userService.getPersonByUsername(message.getAuthor().getUsername());
            if (author.isPresent()) {
                chat = Optional.of(chatService.getOrCreatePeerChatOf(bot, author.get()));
            } else {
                chat = Optional.empty();
            }
        }

        if (chat.isPresent() && author.isPresent()) {
            message.setChat(chat.get());
            message.setAuthor(author.get());
            messageService.saveMessage(message);
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.ok(false);
        }
    }

    /* READ  **************************************************************************************/

    @Override
    @GetMapping("/{chatId}")
    public ResponseEntity<Collection<Message>> getMessages(
        @PathVariable("chatId") Long chatId,
        @PathParam("since") String dateTime,
        @PathParam("from") String username
    ) {
        Optional<LocalDateTime> timestamp = Optional.ofNullable(dateTime)
            .map(LocalDateTime::parse);

        return Optional.ofNullable(chatId)
            .flatMap(id -> chatService.getChatById(id))
            .map(
                chat -> Optional.ofNullable(username).map(
                    author_name -> timestamp.map(
                        // Chat, Author and timestamp present
                        localDateTime -> ResponseEntity
                            .ok(messageService.getMessagesByChatSinceFrom(
                                chat,
                                localDateTime,
                                author_name
                                )
                            )
                    ).orElseGet(
                        // Chat and Author present
                        () -> ResponseEntity
                            .ok(messageService.getAllMessagesByChatFrom(chat, author_name))
                    )
                ).orElseGet(
                    () -> timestamp.map(
                        // Chat and timestamp present
                        localDateTime -> ResponseEntity
                            .ok(messageService.getMessagesByChatSince(chat, localDateTime))
                    ).orElseGet(
                        // Only Chat present
                        () -> ResponseEntity.ok(messageService.getAllMessagesByChat(chat))
                    )
                )
            ).orElseGet(
                // Chat not present
                () -> ResponseEntity.notFound().build()
            );
    }

    /* UPDATE  ************************************************************************************/

    /* DELETE  ************************************************************************************/

}
