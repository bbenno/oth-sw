package de.othr.bib48218.chat.service;

import de.othr.bib48218.chat.entity.Chat;
import de.othr.bib48218.chat.entity.ChatMemberStatus;
import de.othr.bib48218.chat.entity.ChatMembership;
import de.othr.bib48218.chat.entity.User;
import de.othr.bib48218.chat.repository.ChatMembershipRepository;
import de.othr.bib48218.chat.repository.GroupChatRepository;
import de.othr.bib48218.chat.repository.PeerChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class ChatService implements IFChatService {
    @Autowired
    private GroupChatRepository groupRepository;

    @Autowired
    private PeerChatRepository peerRepository;

    @Autowired
    private ChatMembershipRepository chatMembershipRepository;

    @Override
    public Chat getChatById(Long id) {
        return groupRepository.findById(id).orElse(null);
    }

    @Override
    public Collection<Chat> getChatsByUser(User user) {
        Collection<Chat> chats = new LinkedList<>();
        chats.addAll(groupRepository.findByMembershipsUser(user));
        chats.addAll(peerRepository.findByMembershipsUser(user));

        return chats;
    }

    @Override
    public Collection<Chat> getAll() {
        Stream<Chat> groupChatStream = StreamSupport.stream(groupRepository.findAll().spliterator(), false).map((chat) -> chat);
        Stream<Chat> peerChatStream = StreamSupport.stream(peerRepository.findAll().spliterator(), false).map((chat) -> chat);
        return Stream.concat(groupChatStream, peerChatStream).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public ChatMembership addUserToChat(User user, Chat chat) {
        return addUserToChat(user, chat, ChatMemberStatus.MEMBER);
    }

    @Override
    public ChatMembership addUserToChat(User user, Chat chat, ChatMemberStatus status) {
        ChatMembership chatMembership = new ChatMembership(chat, status, user);
        chatMembership = chatMembershipRepository.save(chatMembership);
        return chatMembership;
    }
}
