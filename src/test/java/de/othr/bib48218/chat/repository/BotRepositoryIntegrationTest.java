package de.othr.bib48218.chat.repository;

import de.othr.bib48218.chat.entity.Bot;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BotRepositoryIntegrationTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BotRepository botRepository;

    @Test
    public void whenFindByUsername_thenReturnPerson() {
        // given
        Bot newBot = new Bot(
                "new_bot",
                "",
                null);

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
