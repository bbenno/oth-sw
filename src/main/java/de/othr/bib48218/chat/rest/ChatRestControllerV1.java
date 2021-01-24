package de.othr.bib48218.chat.rest;

import de.othr.bib48218.chat.entity.PeerChat;
import de.othr.bib48218.chat.entity.User;
import de.othr.bib48218.chat.service.IFChatService;
import de.othr.bib48218.chat.service.IFUserService;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webapi/v1/chats")
public class ChatRestControllerV1 implements IFChatRestControllerV1 {

    @Autowired
    private IFChatService chatService;

    @Autowired
    private IFUserService userService;

    /* CREATE  ************************************************************************************/

    /* READ  **************************************************************************************/

    @Override
    @GetMapping()
    public ResponseEntity<Collection<PeerChat>> getAllChats(
        @PathParam("with") String username
    ) {
        return ResponseEntity.of(
            Optional.ofNullable(username)
                .flatMap(n -> userService.getUserByUsername(n))
                .map(
                    u -> chatService.getChatsByUser(u).stream()
                        .filter(c -> c instanceof PeerChat)
                        .map(c-> (PeerChat) c)
                        .collect(Collectors.toUnmodifiableList())
                )
        );
    }

    @Override
    @GetMapping("{id}")
    public ResponseEntity<PeerChat> getChat(@PathVariable("id") Long id) {
        return ResponseEntity.of(chatService.getPeerChatById(id));
    }

    @Override
    @GetMapping("{of}/{with}")
    public ResponseEntity<PeerChat> getChat(
        @PathVariable("of") String username1,
        @PathVariable("with") String username2
    ) {
        Optional<User> user1 = Optional.ofNullable(username1)
            .flatMap(n -> userService.getUserByUsername(n));
        Optional<User> user2 = Optional.ofNullable(username2)
            .flatMap(n -> userService.getUserByUsername(n));

        return ResponseEntity.of(
            user1.flatMap(u1 -> user2.map(u2 -> chatService.getOrCreatePeerChatOf(u1, u2)))
        );
    }

    /* UPDATE  ************************************************************************************/

    /* DELETE  ************************************************************************************/

}
