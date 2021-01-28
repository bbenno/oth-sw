package de.othr.bib48218.chat.rest;

import de.othr.bib48218.chat.entity.Message;
import java.util.Collection;
import org.springframework.http.ResponseEntity;

/**
 * A REST controller (v1 of web API) for {@link de.othr.bib48218.chat.entity.Message}s.
 */
public interface IFMessageRestControllerV1 {

  /* CREATE  ************************************************************************************/

  /**
   * Saves a certain message.
   *
   * @param message the message to save
   * @return whether the message was saved successfully
   */
  ResponseEntity<Boolean> postMessage(Message message);

  /* READ  **************************************************************************************/

  /**
   * Gets all messages in a certain chat since a certain point in time and of a certain author.
   *
   * @param chatId   the number identifying the chat
   * @param dateTime the date time
   * @param username the string identifying the user
   * @return the collection containing the messages
   */
  ResponseEntity<Collection<Message>> getMessages(Long chatId, String dateTime, String username);

  /* UPDATE  ************************************************************************************/

  /* DELETE  ************************************************************************************/

}
