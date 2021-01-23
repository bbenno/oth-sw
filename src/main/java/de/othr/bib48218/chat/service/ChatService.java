package de.othr.bib48218.chat.service;

import de.othr.bib48218.chat.entity.Chat;
import de.othr.bib48218.chat.entity.ChatMemberStatus;
import de.othr.bib48218.chat.entity.ChatMembership;
import de.othr.bib48218.chat.entity.GroupChat;
import de.othr.bib48218.chat.entity.GroupVisibility;
import de.othr.bib48218.chat.entity.PeerChat;
import de.othr.bib48218.chat.entity.User;
import de.othr.bib48218.chat.repository.GroupChatRepository;
import de.othr.bib48218.chat.repository.PeerChatRepository;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChatService implements IFChatService {

    @Autowired
    private GroupChatRepository groupRepository;

    @Autowired
    private PeerChatRepository peerRepository;

    @Override
    @Transactional
    public Optional<Chat> getChatById(Long id) {
        return getGroupChatById(id).map(gc -> (Chat) gc).or(() -> getPeerChatById(id));
    }

    @Override
    @Transactional
    public Optional<GroupChat> getGroupChatById(Long id) {
        return groupRepository.findById(id);
    }

    @Override
    @Transactional
    public Optional<PeerChat> getPeerChatById(Long id) {
        return peerRepository.findById(id);
    }

    @Override
    @Transactional
    public Collection<Chat> getChatsByUser(User user) {
        return Stream.concat(
            groupRepository.findByMembershipsUser(user).stream(),
            peerRepository.findByMembershipsUser(user).stream()
        ).collect(Collectors.toUnmodifiableList());
    }

    @Override
    @Transactional
    public Collection<Chat> getAllChats() {
        return Stream.concat(
            getAllPeerChats().stream(),
            getAllGroupChats().stream()
        ).collect(Collectors.toUnmodifiableList());
    }

    @Override
    @Transactional
    public Collection<GroupChat> getAllGroupChats() {
        return StreamSupport.stream(groupRepository.findAll().spliterator(), false)
            .collect(Collectors.toUnmodifiableList());
    }

    @Override
    @Transactional
    public Collection<GroupChat> getAllPublicGroupChats() {
        return groupRepository.findByVisibilityIs(GroupVisibility.PUBLIC);
    }

    @Override
    @Transactional
    public Collection<PeerChat> getAllPeerChats() {
        return StreamSupport.stream(peerRepository.findAll().spliterator(), false)
            .collect(Collectors.toUnmodifiableList());
    }

    @Override
    @Transactional
    public GroupChat createGroupChat(User creator, GroupVisibility visibility) {
        return saveChat(creator, new GroupChat(visibility));
    }

    @Override
    @Transactional
    public GroupChat saveChat(User creator, GroupChat chat) {
        chat.setMemberships(
            Set.of(new ChatMembership(chat, ChatMemberStatus.ADMINISTRATOR, creator))
        );
        return groupRepository.save(chat);
    }

    @Override
    @Transactional
    public PeerChat saveChat(User creator, PeerChat chat) {
        chat.setMemberships(
            Set.of(new ChatMembership(chat, ChatMemberStatus.ADMINISTRATOR, creator))
        );
        return peerRepository.save(chat);
    }

    @Override
    @Transactional
    public PeerChat getOrCreatePeerChatOf(User user, User otherUser) {
        Collection<PeerChat> chatsOfUser = peerRepository.findByMembershipsUser(user);
        Collection<PeerChat> chatsOfOtherUser = peerRepository.findByMembershipsUser(otherUser);

        return chatsOfUser.stream().distinct()
            .filter(chatsOfOtherUser::contains).findAny()
            .orElseGet(() -> {
                PeerChat peerChat = new PeerChat();
                peerChat.setMemberships(Set.of(
                    new ChatMembership(peerChat, ChatMemberStatus.ADMINISTRATOR, user),
                    new ChatMembership(peerChat, ChatMemberStatus.ADMINISTRATOR, otherUser)
                ));

                return peerRepository.save(peerChat);
            });
    }

    @Override
    @Transactional
    public void deleteChat(@NonNull Chat chat) {
        if (chat instanceof PeerChat) {
            peerRepository.delete((PeerChat) chat);
        } else if (chat instanceof GroupChat) {
            groupRepository.delete((GroupChat) chat);
        }
    }
}
