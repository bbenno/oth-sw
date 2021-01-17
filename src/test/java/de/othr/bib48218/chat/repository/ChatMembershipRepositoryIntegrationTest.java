package de.othr.bib48218.chat.repository;

import de.othr.bib48218.chat.entity.Chat;
import de.othr.bib48218.chat.entity.ChatMembership;
import de.othr.bib48218.chat.entity.User;
import de.othr.bib48218.chat.factory.ChatFactory;
import de.othr.bib48218.chat.factory.ChatMembershipFactory;
import de.othr.bib48218.chat.factory.UserFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("testing")
public class ChatMembershipRepositoryIntegrationTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ChatMembershipRepository chatMembershipRepository;

    @Test
    void shouldFindChatMembershipsByUser() {
        User user = UserFactory.newValidPerson();
        ChatMembership chatMembership = ChatMembershipFactory.newChatMembershipWithUser(user);

        entityManager.persist(chatMembership.getUser());
        entityManager.persist(chatMembership.getChat());
        entityManager.persistAndFlush(chatMembership);

        var found = chatMembershipRepository.findByUser(user);

        assertThat(found).isNotNull();
        assertThat(found).isNotEmpty();
        assertThat(found).contains(chatMembership);
    }

    @Test
    void shouldFindChatMembershipsByChat() {
        Chat chat = ChatFactory.newValidGroupChat();
        ChatMembership chatMembership = ChatMembershipFactory.newChatMembershipWithChat(chat);
        entityManager.persist(chatMembership.getChat());
        entityManager.persist(chatMembership.getUser());
        entityManager.persistAndFlush(chatMembership);

        var found = chatMembershipRepository.findByChat(chat);

        assertThat(found).isNotNull();
        assertThat(found).isNotEmpty();
        assertThat(found).contains(chatMembership);
    }
}
