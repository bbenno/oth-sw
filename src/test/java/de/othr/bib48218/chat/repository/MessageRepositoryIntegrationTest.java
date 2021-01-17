package de.othr.bib48218.chat.repository;

import de.othr.bib48218.chat.entity.Chat;
import de.othr.bib48218.chat.entity.Message;
import de.othr.bib48218.chat.entity.User;
import de.othr.bib48218.chat.factory.ChatFactory;
import de.othr.bib48218.chat.factory.MessageFactory;
import de.othr.bib48218.chat.factory.UserFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("testing")
public class MessageRepositoryIntegrationTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MessageRepository messageRepository;

    @Test
    void shouldFindMessagesByAuthor() {
        Chat chat = ChatFactory.newValidGroupChat();
        User author = UserFactory.newValidPerson();
        Message message = MessageFactory.newMessage(chat, author);

        chat = entityManager.persist(chat);
        author = entityManager.persist(author);
        message = entityManager.persistAndFlush(message);

        Collection<Message> messages = messageRepository.findByAuthor(author);

        assertThat(messages).isNotNull();
        assertThat(messages).isNotEmpty();
        assertThat(messages).contains(message);
    }

    @Test
    void shouldFindMessagesByChat() {
        Chat chat = ChatFactory.newValidGroupChat();
        User author = UserFactory.newValidPerson();
        Message message = MessageFactory.newMessage(chat, author);

        chat = entityManager.persist(chat);
        author = entityManager.persist(author);
        message = entityManager.persistAndFlush(message);

        Collection<Message> messages = messageRepository.findByChat(chat);

        assertThat(messages).isNotNull();
        assertThat(messages).isNotEmpty();
        assertThat(messages).contains(message);
    }
}
