package de.othr.bib48218.chat.service;

import de.othr.bib48218.chat.entity.Message;
import de.othr.bib48218.chat.entity.User;
import java.util.Collection;
import java.util.Optional;

@SuppressWarnings("unused")
public interface IFSendMessage {

    /**
     * Gets the user with a certain username
     *
     * @param username the string identifying user
     * @return the user
     */
    Optional<User> findUserByUsername(String username);

    /**
     * Saves a certain message.
     *
     * @param message the message
     * @return <code>true</code> if sending was successful; otherwise <code>false</code>
     */
    Boolean sendMessage(Message message);

    /**
     * Gets all messages.
     *
     * @return the collection containing all messages
     */
    Collection<Message> pullMessages();

}
