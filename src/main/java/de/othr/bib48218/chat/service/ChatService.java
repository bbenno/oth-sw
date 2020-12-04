package de.othr.bib48218.chat.service;

import de.othr.bib48218.chat.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService implements IFChatService {
    @Autowired
    private ChatRepository repository;
}
