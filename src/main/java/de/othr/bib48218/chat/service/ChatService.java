package de.othr.bib48218.chat.service;

import de.othr.bib48218.chat.entity.PeerChat;
import de.othr.bib48218.chat.entity.User;
import de.othr.bib48218.chat.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChatService implements IFChatService {
    @Qualifier("groupChatRepository")
    @Autowired
    private ChatRepository groupChatRepository;

    @Qualifier("peerChatRepository")
    @Autowired
    private ChatRepository peerChatRepository;

    @Override
    public Optional<PeerChat> getChatBetweenUsers(User user1, User user2) {
        return Optional.empty();
    }
}
