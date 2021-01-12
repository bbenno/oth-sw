package de.othr.bib48218.chat.service;

import de.othr.bib48218.chat.entity.Chat;
import de.othr.bib48218.chat.entity.Message;

import java.time.LocalDateTime;
import java.util.Collection;

public interface IFMessageService extends IFSendMessage {
    /**
     * Returns all messages of a given chat.
     *
     * @param chat the chat whose messages to receive
     * @return messages
     */
    Collection<Message> getAllMessagesByChat(Chat chat);

    /**
     * Returns all messages of a given chat since a given time.
     *
     * @param chat the chat whose messages to receive
     * @param time the point in time since when the messages should be returned
     * @return messages
     */
    Collection<Message> getMessagesByChatSince(Chat chat, LocalDateTime time);

    /**
     * Returns all messages of given chat from given author.
     *
     * @param chat     the chat whose messages to receive
     * @param username the name of the author
     * @return messages
     */
    Collection<Message> getAllMessagesByChatFrom(Chat chat, String username);

    /**
     * Returns all messages of a given chat since a given time by a given author.
     *
     * @param chat          the chat whose messages to receive
     * @param localDateTime the point in time since when the messages should be returned
     * @param username      the name of the author
     * @return messages
     */
    Collection<Message> getMessagesByChatSinceFrom(Chat chat, LocalDateTime localDateTime, String username);

    /**
     * Saves given message.
     *
     * @param message the message to save
     * @return saved message
     */
    Message saveMessage(Message message);

    /**
     * Deletes message with the given id.
     *
     * @param id the id of the message to delete
     */
    void deleteMessageById(Long id);

    /**
     * Delete given message
     *
     * @param message the message to delete
     */
    void deleteMessage(Message message);
}
