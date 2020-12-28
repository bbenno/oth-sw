package de.othr.bib48218.chat.repository;

import de.othr.bib48218.chat.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MessageRepositoryIntegrationTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MessageRepository messageRepository;

    @Test
    void shouldFindMessagesByAuthor() {
        Chat chat = new GroupChat();
        User author = new Person("person_1", "password");
        Message message = new Message("Hello world", chat, author, LocalDateTime.now());
        entityManager.persistAndFlush(message);

        Collection<Message> messages = messageRepository.findByAuthor(author);

        assertThat(messages).isNotNull();
        assertThat(messages).contains(message);
    }
}
