package de.othr.bib48218.chat.service;

import de.othr.bib48218.chat.entity.Bot;
import de.othr.bib48218.chat.entity.Person;
import de.othr.bib48218.chat.entity.User;
import de.othr.bib48218.chat.repository.BotRepository;
import de.othr.bib48218.chat.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserServiceIntegrationTest {
    @Autowired
    private IFUserService userService;
    @MockBean
    private PersonRepository personRepository;
    @MockBean
    private BotRepository botRepository;

    @BeforeEach
    void setUp() {
        Person joe = new Person(
                "joe",
                "",
                "Joe",
                "Smith",
                "joe@smith.com");
        Bot bot = new Bot(
                "botty",
                "");

        Mockito.when(personRepository.findByFirstName(joe.getFirstName())).thenReturn(joe);
        Mockito.when(personRepository.findByUsername(joe.getUsername())).thenReturn(joe);
        Mockito.when(botRepository.findByUsername(bot.getUsername())).thenReturn(bot);
    }

    @Test
    void whenValidFirstName_thenPersonShouldBeFound() {
        // given
        String firstName = "Joe";

        // when
        Person found = userService.getPersonByFirstName(firstName);

        // then
        assertThat(found).isNotNull();
        assertThat(found.getFirstName()).isEqualTo(firstName);
    }

    @Test
    void whenValidBotUsername_thenBotShouldBeFound() {
        // given
        String username = "botty";

        // when
        Bot found = userService.getBotByUsername(username);

        // then
        assertThat(found).isNotNull();
        assertThat(found.getUsername()).isEqualTo(username);
    }

    @Test
    void whenValidPersonUsername_thenPersonShouldBeFound() {
        // given
        String username = "joe";

        // when
        Person found = userService.getPersonByUsername(username);

        // then
        assertThat(found).isNotNull();
        assertThat(found.getUsername()).isEqualTo(username);
    }

    @Test
    void whenValidUsername_thenUserShouldBeFound() {
        // given
        String username = "botty";

        // when
        User found = userService.getUserByUsername(username);

        // then
        assertThat(found).isNotNull();
        assertThat(found.getUsername()).isEqualTo(username);
    }

    @TestConfiguration
    static class UserServiceTestContextConfiguration {
        @Bean
        IFUserService ifUserService() {
            return new UserService();
        }
    }
}
