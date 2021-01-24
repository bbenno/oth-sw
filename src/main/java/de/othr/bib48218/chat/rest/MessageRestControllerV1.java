package de.othr.bib48218.chat.rest;

import de.othr.bib48218.chat.entity.Message;
import de.othr.bib48218.chat.service.IFChatService;
import de.othr.bib48218.chat.service.IFMessageService;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import javax.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webapi/v1/")
public class MessageRestControllerV1 implements IFMessageRestControllerV1 {

    @Autowired
    private IFMessageService messageService;

    @Autowired
    private IFChatService chatService;

    /* CREATE  ************************************************************************************/

    @Override
    @PostMapping("messages")
    public ResponseEntity<Message> postMessage(Message message) {
        return ResponseEntity.of(Optional.ofNullable(messageService.saveMessage(message)));
    }

    /* READ  **************************************************************************************/

    @Override
    @GetMapping("messages")
    public ResponseEntity<Collection<Message>> getMessages(
        @PathParam("chat") Long chatId,
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
