package de.othr.bib48218.chat.rest;

import de.othr.bib48218.chat.entity.PeerChat;
import java.util.Collection;
import javax.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface IFChatRestControllerV1 {

    /* CREATE  ************************************************************************************/

    /* READ  **************************************************************************************/

    ResponseEntity<Collection<PeerChat>> getAllChats(@PathParam("with") String username);

    ResponseEntity<PeerChat> getChat(@PathVariable("id") Long id);

    /**
     * Returns peer chat between two users.
     *
     * <p>
     * If there is not yet a peer chat of the two users a new one will be created.
     * </p>
     *
     * @param username1 the username of an user of the chat
     * @param username2 the username of an user of the chat
     * @return the peer chat
     */
    ResponseEntity<PeerChat> getChat(String username1, String username2);

    /* UPDATE  ************************************************************************************/

    /* DELETE  ************************************************************************************/

}
