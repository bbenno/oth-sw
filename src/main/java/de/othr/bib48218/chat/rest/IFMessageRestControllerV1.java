package de.othr.bib48218.chat.rest;

import de.othr.bib48218.chat.entity.Message;
import java.util.Collection;
import org.springframework.http.ResponseEntity;

public interface IFMessageRestControllerV1 {

    /* CREATE  ************************************************************************************/

    /**
     * Saves given message.
     *
     * @param message the message to save
     * @return saved message
     */
    ResponseEntity<Message> postMessage(Message message);

    /* READ  **************************************************************************************/

    /**
     * Returns messages
     *
     * @param chatId   the id of the chat whose messages to receive
     * @param dateTime the optional date time since when messages to receive
     * @param username the username of the author
     * @return messages matching parameter constraint
     */
    ResponseEntity<Collection<Message>> getMessages(Long chatId, String dateTime, String username);

    /* UPDATE  ************************************************************************************/

    /* DELETE  ************************************************************************************/

}
