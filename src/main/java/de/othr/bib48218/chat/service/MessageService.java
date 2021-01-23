package de.othr.bib48218.chat.service;

import de.othr.bib48218.chat.entity.Chat;
import de.othr.bib48218.chat.entity.Message;
import de.othr.bib48218.chat.entity.User;
import de.othr.bib48218.chat.repository.MessageRepository;
import de.othr.bib48218.chat.repository.PersonRepository;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MessageService implements IFMessageService {

    @Autowired
    private MessageRepository repository;

    @Autowired
    private PersonRepository userRepository;

    @Override
    @Transactional
    public Collection<Message> getAllMessagesByChat(Chat chat) {
        return repository.findByChat(chat);
    }

    @Override
    @Transactional
    public Collection<Message> getMessagesByChatSince(Chat chat, LocalDateTime time) {
        return repository.findByChatAndTimestampBefore(chat, time);
    }

    @Override
    @Transactional
    public Collection<Message> getAllMessagesByChatFrom(Chat chat, String username) {
        return repository.findByChatAndAuthor_Username(chat, username);
    }

    @Override
    @Transactional
    public Collection<Message> getMessagesByChatSinceFrom(
        Chat chat,
        LocalDateTime localDateTime,
        String username
    ) {
        return repository
            .findByChatAndTimestampBeforeAndAuthor_Username(chat, localDateTime, username);
    }

    @Override
    @Transactional
    public Message saveMessage(Message message) {
        return repository.save(message);
    }

    @Override
    @Transactional
    public void deleteMessageById(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteMessage(Message message) {
        repository.delete(message);
    }

    @Override
    @Transactional
    public Optional<User> findUserByUsername(String username, String serviceToken) {
        // TODO: Check serviceToken
        return userRepository.findById(username).map((person) -> person);
    }

    @Override
    @Transactional
    public Boolean sendMessage(Message message, String serviceToken) {
        // TODO: Check serviceToken
        try {
            repository.save(message);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public Collection<Message> pullMessages(String serviceToken) {
        // TODO: Check serviceToken
        User serviceUser = null;
        // TODO: Find all messages of all chats
        // return repository.findByUser(serviceUser);
        return null;
    }
}
