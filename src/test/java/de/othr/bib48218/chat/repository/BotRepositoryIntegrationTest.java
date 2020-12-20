package de.othr.bib48218.chat.repository;

import de.othr.bib48218.chat.entity.Bot;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BotRepositoryIntegrationTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BotRepository botRepository;

    @Test
    void whenFindByUsername_thenReturnPerson() {
        // given
        Bot newBot = new Bot(
                "new_bot",
                "");

        entityManager.persist(newBot);
        entityManager.flush();

        // when
        Bot found = botRepository.findByUsername(newBot.getUsername());

        // then
        assertThat(found).isNotNull();
        assertThat(found.getUsername()).isEqualTo(newBot.getUsername());
        assertThat(found).isEqualTo(newBot);
    }
}
