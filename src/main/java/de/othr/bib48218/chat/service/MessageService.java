package de.othr.bib48218.chat.service;

import de.othr.bib48218.chat.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService implements IFMessageService {
    @Autowired
    private MessageRepository repository;
}
