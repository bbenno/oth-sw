package de.othr.bib48218.chat.service;

import de.othr.bib48218.chat.entity.Chat;
import de.othr.bib48218.chat.entity.User;
import de.othr.bib48218.chat.repository.GroupChatRepository;
import de.othr.bib48218.chat.repository.PeerChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.LinkedList;

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

    @Override
    public Collection<Chat> getChatsByUser(User user) {
        Collection<Chat> chats = new LinkedList<>();
        //chats.addAll(groupRepository.findAllByMembershipsUser(user));
        //chats.addAll(peerRepository.findAllByUsersUsername(user.getUsername()));

        return chats;
    }
}
