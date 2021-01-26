package de.othr.bib48218.chat.service;

import de.othr.bib48218.chat.entity.Chat;
import de.othr.bib48218.chat.entity.Message;
import java.time.LocalDateTime;
import java.util.Collection;

@SuppressWarnings("unused")
public interface IFMessageService {

    /* GET Messages  ******************************************************************************/

    /**
     * Gets all messages of a certain chat.
     *
     * @param chat the chat
     * @return the collection of the messages
     */
    Collection<Message> getAllMessagesByChat(Chat chat);

    /**
     * Gets all messages of a certain chat since a certain point in time.
     *
     * @param chat the chat
     * @param time the point in time
     * @return the collection of the messages
     */
    Collection<Message> getMessagesByChatSince(Chat chat, LocalDateTime time);

    /**
     * Gets all messages of certain chat sent by certain user.
     *
     * @param chat     the chat
     * @param username the string identifying the user being author of the messages
     * @return the collection of the messages
     */
    Collection<Message> getAllMessagesByChatFrom(Chat chat, String username);

    /**
     * Gets all messages of a certain chat since a certain point in time, sent by certain user.
     *
     * @param chat          the chat
     * @param localDateTime the point in time
     * @param username      the string identifying the user being author of the messages
     * @return messages
     */
    Collection<Message> getMessagesByChatSinceFrom(Chat chat, LocalDateTime localDateTime,
        String username);

    /* ADD Message * ******************************************************************************/

    /**
     * Saves a certain message.
     *
     * @param message the message
     * @return the saved message
     */
    Message saveMessage(Message message);

    /* DELETE Messages  ***************************************************************************/

    /**
     * Deletes the message with a certain id.
     *
     * @param id the id of the message to delete
     */
    void deleteMessageById(Long id);

    /**
     * Delete a certain message.
     *
     * @param message the message
     */
    void deleteMessage(Message message);
}
