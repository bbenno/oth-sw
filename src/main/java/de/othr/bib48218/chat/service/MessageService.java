package de.othr.bib48218.chat.service;

import de.othr.bib48218.chat.entity.Message;
import de.othr.bib48218.chat.entity.User;
import de.othr.bib48218.chat.repository.MessageRepository;
import de.othr.bib48218.chat.repository.UserRepository;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class MessageService implements IFMessageService {
    @Autowired
    private MessageRepository repository;
    @Qualifier("personRepository")
    @Autowired
    private UserRepository userRepository;

    @Override
    public User findUserByUsername(String username, String serviceToken) {
        // TODO: Check serviceToken
        return userRepository.findByUsername(username);
    }

    @Override
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
    public Collection<Message> pullMessages(String serviceToken) {
        // TODO: Check serviceToken
        User serviceUser = null;
        // TODO: Find all messages of all chats
        // return repository.findByUser(serviceUser);
        return null;
    }
}
