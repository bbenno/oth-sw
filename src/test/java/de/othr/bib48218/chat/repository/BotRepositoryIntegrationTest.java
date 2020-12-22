package de.othr.bib48218.chat.repository;

import de.othr.bib48218.chat.entity.Bot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BotRepositoryIntegrationTest {
    private final Bot bot = new Bot(
        "new_bot",
        "");

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BotRepository botRepository;

    @BeforeEach
    void setup() {
        entityManager.persist(bot);
        entityManager.flush();
    }

    @Test
    void whenFindByUsername_thenReturnPerson() {
        Bot found = botRepository.findByUsername(bot.getUsername());

        assertThat(found).isNotNull();
        assertThat(found.getUsername()).isEqualTo(bot.getUsername());
        assertThat(found).isEqualTo(bot);
    }

    @Test
    void usernameShouldBeUnique() {
        var bot2 = new Bot(bot.getUsername(), ".-.");
        assertThrows(Exception.class, () -> entityManager.persist(bot2));
    }
}
