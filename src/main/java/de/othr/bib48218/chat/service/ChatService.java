package de.othr.bib48218.chat.service;

import de.othr.bib48218.chat.entity.*;
import de.othr.bib48218.chat.repository.ChatMembershipRepository;
import de.othr.bib48218.chat.repository.GroupChatRepository;
import de.othr.bib48218.chat.repository.PeerChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;
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
    public Optional<? extends Chat> getChatById(Long id) {
        var groupChat = getGroupChatById(id);
        if (groupChat.isPresent())
            return groupChat;
        else
            return getPeerChatById(id);
    }

    @Override
    public Optional<GroupChat> getGroupChatById(Long id) {
        return groupRepository.findById(id);
    }

    @Override
    public Optional<PeerChat> getPeerChatById(Long id) {
        return peerRepository.findById(id);
    }

    @Override
    public Collection<Chat> getChatsByUser(User user) {
        Collection<Chat> chats = new LinkedList<>();
        chats.addAll(groupRepository.findByMembershipsUser(user));
        chats.addAll(peerRepository.findByMembershipsUser(user));

        return chats;
    }

    @Override
    public Collection<Chat> getAllChats() {
        Stream<Chat> groupChatStream = StreamSupport.stream(groupRepository.findAll().spliterator(), false).map((chat) -> chat);
        Stream<Chat> peerChatStream = StreamSupport.stream(peerRepository.findAll().spliterator(), false).map((chat) -> chat);
        return Stream.concat(groupChatStream, peerChatStream).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Collection<GroupChat> getAllGroupChats() {
        Stream<GroupChat> groupChatStream = StreamSupport.stream(groupRepository.findAll().spliterator(), false);

        return groupChatStream.collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Collection<GroupChat> getAllPublicGroupChats() {
        return groupRepository.findByVisibilityIs(GroupVisibility.PUBLIC);
    }

    @Override
    public Collection<PeerChat> getAllPeerChats() {
        Stream<PeerChat> peerChatStream = StreamSupport.stream(peerRepository.findAll().spliterator(), false);

        return peerChatStream.collect(Collectors.toUnmodifiableList());
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

    @Override
    public GroupChat createGroupChat(User creator, GroupVisibility visibility) {
        return saveChat(creator, new GroupChat(visibility));
    }

    @Override
    public GroupChat saveChat(User creator, GroupChat chat) {
        chat = groupRepository.save(chat);
        addUserToChat(creator, chat, ChatMemberStatus.ADMINISTRATOR);
        return chat;
    }

    @Override
    public PeerChat saveChat(User creator, PeerChat chat) {
        chat = peerRepository.save(chat);
        addUserToChat(creator, chat, ChatMemberStatus.ADMINISTRATOR);
        return chat;
    }

    @Override
    public PeerChat getOrCreatePeerChatOf(User user, User otherUser) {
        Collection<PeerChat> chatsOfUser = peerRepository.findByMembershipsUser(user);
        Collection<PeerChat> chatsOfOtherUser = peerRepository.findByMembershipsUser(otherUser);

        Optional<PeerChat> chat = chatsOfUser.stream().distinct().filter(chatsOfOtherUser::contains).findAny();

        if (chat.isPresent()) {
            return chat.get();
        } else {
            // Create new PeerChat
            PeerChat peerChat = new PeerChat();
            peerChat = peerRepository.save(peerChat);
            addUserToChat(user, peerChat);
            addUserToChat(otherUser, peerChat);

            return peerChat;
        }
    }

    @Override
    public void deleteChat(Long id) {
        Optional<? extends Chat> chat = getChatById(id);
        chat.ifPresent(this::deleteChat);
    }

    @Override
    public void deleteChat(Chat chat) {
        if (chat.getClass().equals(GroupChat.class))
            groupRepository.delete((GroupChat) chat);
        else if (chat.getClass().equals(PeerChat.class))
            peerRepository.delete((PeerChat) chat);
    }

    @Override
    public Optional<ChatMemberStatus> getChatMembership(Chat chat, User user) {
        var memberships_chat = chatMembershipRepository.findByChat(chat);
        return memberships_chat.stream().filter(m -> m.getUser().equals(user)).map(ChatMembership::getStatus).findFirst();
    }
}
