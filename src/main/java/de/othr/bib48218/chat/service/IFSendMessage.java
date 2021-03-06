package de.othr.bib48218.chat.service;

import de.othr.bib48218.chat.entity.Chat;
import de.othr.bib48218.chat.entity.Message;
import de.othr.bib48218.chat.entity.User;
import java.time.LocalDateTime;
import java.util.Collection;

@SuppressWarnings("unused")
public interface IFSendMessage {

    /**
     * Gets the user with a certain username
     *
     * @param username the string identifying a user
     * @return the user
     */
    User getUser(String username);

    /**
     * Gets the chat of the user identified by its username with the service bot.
     *
     * @param username the string identifying a user
     * @return the chat
     */
    Chat getChatWithUserByUsername(String username);

    /**
     * Saves a certain message.
     *
     * @param message the message
     * @return <code>true</code> if sending message successfully; otherwise <code>false</code>
     */
    Boolean sendMessage(Message message);

    /**
     * Gets the messages of a certain chat since a certain point in time.
     *
     * @param chat the chat
     * @param dateTime the point in time
     * @return the collection containing all messages
     */
    Collection<Message> pullMessages(Chat chat, LocalDateTime dateTime);

}
