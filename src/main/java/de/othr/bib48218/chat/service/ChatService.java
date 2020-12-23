package de.othr.bib48218.chat.service;

import de.othr.bib48218.chat.entity.Chat;
import de.othr.bib48218.chat.entity.PeerChat;
import de.othr.bib48218.chat.repository.ChatRepository;
import de.othr.bib48218.chat.repository.GroupChatRepository;
import de.othr.bib48218.chat.repository.PeerChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ChatService implements IFChatService {
    @Autowired
    private GroupChatRepository groupRepository;

    @Autowired
    private PeerChatRepository peerRepository;

    @Override
    public Chat getChatById(Long id) {
        return groupRepository.findById(id).get();
    }
}
