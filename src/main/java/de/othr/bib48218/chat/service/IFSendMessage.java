package de.othr.bib48218.chat.service;

import java.util.Collection;
import java.util.Optional;
import de.othr.bib48218.chat.entity.Message;
import de.othr.bib48218.chat.entity.User;

public interface IFSendMessage {
    /**
     * Returns User by given username
     * @param username string identifying user
     * @param serviceToken credential token of foreign service
     * @return user
     */
    Optional<User> findUserByUsername(String username, String serviceToken);

    /**
     * Sends a given message.
     * The message must reference valid chat.
     *
     * @param message message to send
     * @param serviceToken credential token of foreign service
     * @return <code>true</code> if sending was successful; otherwiese <code>false</code>
     */
    Boolean sendMessage(Message message, String serviceToken);

    /**
     * Returns all messages for foreign partner.
     * @param serviceToken credential token of foreign service
     * @return messages
     */
    Collection<Message> pullMessages(String serviceToken);
}
