package de.othr.bib48218.chat.rest;

import de.othr.bib48218.chat.entity.PeerChat;
import java.util.Collection;
import javax.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * A REST controller (v1 of web API) for {@link de.othr.bib48218.chat.entity.Chat}s.
 */
public interface IFChatRestControllerV1 {

    /* CREATE  ************************************************************************************/

    /* READ  **************************************************************************************/

    /**
     * Gets all peer chats where an user with certain username is member of.
     *
     * @param username the string identifying an user
     * @return the collection containing all peer chats
     */
    ResponseEntity<Collection<PeerChat>> getAllChats(@PathParam("with") String username);

    /**
     * Gets the peer chat uniquely identified by a certain id.
     *
     * @param id the number identifying an chat
     * @return the peer chat, or <code>null</code> if there no peer chat identified by id
     */
    ResponseEntity<PeerChat> getChat(@PathVariable("id") Long id);

    /**
     * Gets peer chat where two users with certain usernames are member of.
     *
     * <p>
     * If there is not yet a peer chat of the two users a new one will be created.
     *
     * @param username1 the string identifying an user
     * @param username2 the string identifying an user
     * @return the peer chat
     */
    ResponseEntity<PeerChat> getChat(String username1, String username2);

    /* UPDATE  ************************************************************************************/

    /* DELETE  ************************************************************************************/

}
