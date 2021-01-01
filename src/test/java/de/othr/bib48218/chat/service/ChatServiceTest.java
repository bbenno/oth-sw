package de.othr.bib48218.chat.service;

import com.github.javafaker.Faker;
import de.othr.bib48218.chat.entity.*;
import de.othr.bib48218.chat.factory.ChatFactory;
import de.othr.bib48218.chat.factory.ChatMembershipFactory;
import de.othr.bib48218.chat.factory.UserFactory;
import de.othr.bib48218.chat.repository.ChatMembershipRepository;
import de.othr.bib48218.chat.repository.GroupChatRepository;
import de.othr.bib48218.chat.repository.PeerChatRepository;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ChatServiceTest {
    @InjectMocks
    private ChatService chatService;

    @Mock
    private GroupChatRepository groupChatRepository;

    @Mock
    private PeerChatRepository peerChatRepository;

    @Mock
    private ChatMembershipRepository chatMembershipRepository;

    @Test
    void getChatByIdShouldReturnChat() {
        Long id = id();
        GroupChat chat = ChatFactory.newValidGroupChat();
        when(groupChatRepository.findById(id)).thenReturn(Optional.of(chat));

        Chat chatFound = chatService.getChatById(id).get();

        assertThat(chatFound).isNotNull();
    }

    @Test
    void getChatByIdShouldReturnNull() {
        // ToDo
    }

    @Test
    void getChatsByUserShouldReturnGroupChats() {
        GroupChat chat = ChatFactory.newValidGroupChat();
        User user = UserFactory.newValidPerson();
        when(groupChatRepository.findByMembershipsUser(user)).thenReturn(Set.of(chat));

        var chats = chatService.getChatsByUser(user);

        assertThat(chats).isNotNull();
        assertThat(chats).contains(chat);
    }

    @Test
    void getChatsByUserShouldReturnPeerChats() {
        PeerChat chat = ChatFactory.newValidPeerChat();
        User user = UserFactory.newValidPerson();
        when(peerChatRepository.findByMembershipsUser(user)).thenReturn(Set.of(chat));

        var chats = chatService.getChatsByUser(user);

        assertThat(chats).isNotNull();
        assertThat(chats).contains(chat);
    }

    @Test
    void getChatsByUserShouldReturnEmtpyCollectionIfNoChatsArePresent() {
        User user = UserFactory.newValidPerson();

        var chats = chatService.getChatsByUser(user);

        assertThat(chats).isNotNull();
        assertThat(chats).isEmpty();
    }

    @Test
    void getAllShouldNotReturnNull() {
        when(groupChatRepository.findAll()).thenReturn(new ArrayList<>(0));
        when(peerChatRepository.findAll()).thenReturn(new ArrayList<>(0));

        var allChats = chatService.getAll();

        assertThat(allChats).isNotNull();
    }

    @Test
    void getAllShouldContainExistingGroupChat() {
        GroupChat chat = ChatFactory.newValidGroupChat();
        var chats = new ArrayList<GroupChat>(1);
        chats.add(chat);
        when(groupChatRepository.findAll()).thenReturn(chats);

        var allChats = chatService.getAll();

        assertThat(allChats).contains(chat);
    }

    @Test
    void getAllShouldContainExistingPeerChat() {
        PeerChat chat = ChatFactory.newValidPeerChat();
        var chats = new ArrayList<PeerChat>(1);
        chats.add(chat);
        when(peerChatRepository.findAll()).thenReturn(chats);

        var allChats = chatService.getAll();

        assertThat(allChats).contains(chat);
    }

    @Test
    void shouldReturnChatMembershipWhenAddingUserToChat() {
        User user = anyUser();
        Chat chat = anyChat();

        when(chatMembershipRepository.save(any(ChatMembership.class))).then(i -> i.getArgument(0, ChatMembership.class));

        var chatMembership = chatService.addUserToChat(user, chat);

        assertThat(chatMembership).isNotNull();
        assertThat(chatMembership.getChat()).isEqualTo(chat);
        assertThat(chatMembership.getUser()).isEqualTo(user);
    }


    @Test
    void shouldReturnChatMembershipWhenAddingUserToChatWithStatus() {
        User user = anyUser();
        Chat chat = anyChat();
        ChatMemberStatus status = ChatMemberStatus.MEMBER;

        when(chatMembershipRepository.save(any(ChatMembership.class))).then(i -> i.getArgument(0, ChatMembership.class));

        var chatMembership = chatService.addUserToChat(user, chat, status);

        assertThat(chatMembership).isNotNull();
        assertThat(chatMembership.getChat()).isEqualTo(chat);
        assertThat(chatMembership.getUser()).isEqualTo(user);
        assertThat(chatMembership.getStatus()).isEqualTo(status);
    }

    @Test
    void shouldReturnGroupChatWhenCreateGroupChat() {
        User user = anyUser();
        GroupVisibility visibility = GroupVisibility.PRIVATE;
        when(groupChatRepository.save(any(GroupChat.class))).thenAnswer(i -> i.getArgument(0, GroupChat.class));

        var groupChat = chatService.createGroupChat(user, visibility);

        assertThat(groupChat).isNotNull();
        assertThat(groupChat.getVisibility()).isEqualTo(visibility);
    }

    private static long id() {
        return Faker.instance().number().randomNumber();
    }

    private static User anyUser() {
        return UserFactory.newValidPerson();
    }

    private static Chat anyChat() {
        return ChatFactory.newValidGroupChat();
    }
}
