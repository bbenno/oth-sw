package de.othr.bib48218.chat.repository;

import de.othr.bib48218.chat.entity.Bot;
import de.othr.bib48218.chat.factory.UserFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@ActiveProfiles("testing")
class BotRepositoryIntegrationTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BotRepository botRepository;

    @Test
    void whenFindByUsername_thenReturnPerson() {
        Bot bot = UserFactory.newValidBot();
        bot = entityManager.persistAndFlush(bot);

        Optional<Bot> found = botRepository.findByUsername(bot.getUsername());

        assertThat(found).isPresent();
        assertThat(found.get().getUsername()).isEqualTo(bot.getUsername());
        assertThat(found.get()).isEqualTo(bot);
    }

    @Test
    void saveValidBot() {
        Bot bot = UserFactory.newValidBot();

        Bot savedBot = botRepository.save(bot);

        assertThat(savedBot).isNotNull();
        assertThat(savedBot).isEqualTo(bot);
    }

    @Test
    void usernameShouldBeUnique() {
        Bot bot = UserFactory.newValidBot();
        bot = entityManager.persistAndFlush(bot);

        Bot bot2 = UserFactory.newValidBotWithUsername(bot.getUsername());
        assertThrows(Exception.class, () -> entityManager.persist(bot2));
    }
}
