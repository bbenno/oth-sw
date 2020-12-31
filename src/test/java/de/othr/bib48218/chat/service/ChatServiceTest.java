package de.othr.bib48218.chat.service;

import com.github.javafaker.Faker;
import de.othr.bib48218.chat.entity.Chat;
import de.othr.bib48218.chat.entity.GroupChat;
import de.othr.bib48218.chat.entity.PeerChat;
import de.othr.bib48218.chat.entity.User;
import de.othr.bib48218.chat.factory.ChatFactory;
import de.othr.bib48218.chat.factory.UserFactory;
import de.othr.bib48218.chat.repository.GroupChatRepository;
import de.othr.bib48218.chat.repository.PeerChatRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ChatServiceTest {
    @InjectMocks
    private ChatService chatService;

    @Mock
    private GroupChatRepository groupChatRepository;

    @Mock
    private PeerChatRepository peerChatRepository;

    @Test
    void getChatByIdShouldReturnChat() {
        Long id = id();
        GroupChat chat = ChatFactory.newValidGroupChat();
        when(groupChatRepository.findById(id)).thenReturn(Optional.of(chat));

        Chat chatFound = chatService.getChatById(id);

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

    private long id() {
        return Faker.instance().number().randomNumber();
    }
}
