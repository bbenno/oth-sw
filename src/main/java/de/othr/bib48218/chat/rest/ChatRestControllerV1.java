package de.othr.bib48218.chat.rest;

import de.othr.bib48218.chat.entity.PeerChat;
import de.othr.bib48218.chat.entity.User;
import de.othr.bib48218.chat.service.IFChatService;
import de.othr.bib48218.chat.service.IFUserService;
import java.util.Optional;
import javax.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webapi/v1/")
public class ChatRestControllerV1 implements IFChatRestControllerV1 {

    @Autowired
    private IFChatService chatService;

    @Autowired
    private IFUserService userService;

    /* CREATE  ************************************************************************************/

    /* READ  **************************************************************************************/

    @Override
    @GetMapping("chats")
    public ResponseEntity<PeerChat> getChat(
        @PathParam("username1") String username1,
        @PathParam("username1") String username2
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
