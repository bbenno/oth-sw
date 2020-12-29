package de.othr.bib48218.chat.service;

import de.othr.bib48218.chat.UserAlreadyExists;
import de.othr.bib48218.chat.entity.Bot;
import de.othr.bib48218.chat.entity.Person;
import de.othr.bib48218.chat.entity.User;
import de.othr.bib48218.chat.factory.UserFactory;
import de.othr.bib48218.chat.repository.BotRepository;
import de.othr.bib48218.chat.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class UserServiceIntegrationTest {
    @Autowired
    private IFUserService userService;

    @MockBean
    private PersonRepository personRepository;

    @MockBean
    private BotRepository botRepository;

    @Test
    void shouldGetPersonByFirstNameIfExisting() {
        Person person = UserFactory.newValidPerson();
        String firstName = person.getFirstName();
        Mockito.when(personRepository.findByFirstName(firstName)).thenReturn(Optional.of(person));

        Person found = userService.getPersonByFirstName(firstName);

        assertThat(found).isNotNull();
        assertThat(found.getFirstName()).isEqualTo(firstName);
        assertThat(found).isEqualTo(person);
    }

    @Test
    void shouldGetBotByUsernameIfExisting() {
        Bot bot = UserFactory.newValidBot();
        String username = bot.getUsername();
        Mockito.when(botRepository.findByUsername(username)).thenReturn(Optional.of(bot));

        Bot found = userService.getBotByUsername(username);

        assertThat(found).isNotNull();
        assertThat(found.getUsername()).isEqualTo(username);
        assertThat(found).isEqualTo(bot);
    }

    @Test
    void shouldGetPersonByUsernameIfExisting() {
        Person person = UserFactory.newValidPerson();
        String username = person.getUsername();
        Mockito.when(personRepository.findByUsername(username)).thenReturn(Optional.of(person));

        Person found = userService.getPersonByUsername(username);

        assertThat(found).isNotNull();
        assertThat(found.getUsername()).isEqualTo(username);
        assertThat(found).isEqualTo(person);
    }

    @Test
    void shouldGetUserByUsernameIfPersonExisting() {
        Person user = UserFactory.newValidPerson();
        String username = user.getUsername();
        Mockito.when(botRepository.findByUsername(username)).thenReturn(Optional.empty());
        Mockito.when(personRepository.findByUsername(username)).thenReturn(Optional.of(user));

        User found = userService.getUserByUsername(username);

        assertThat(found).isNotNull();
        assertThat(found.getUsername()).isEqualTo(username);
        assertThat(found).isEqualTo(user);
    }

    @Test
    void shouldGetUserByUsernameIfBotExisting() {
        Bot user = UserFactory.newValidBot();
        String username = user.getUsername();
        Mockito.when(botRepository.findByUsername(username)).thenReturn(Optional.of(user));
        Mockito.when(personRepository.findByUsername(username)).thenReturn(Optional.empty());

        User found = userService.getUserByUsername(username);

        assertThat(found).isNotNull();
        assertThat(found.getUsername()).isEqualTo(username);
        assertThat(found).isEqualTo(user);
    }

    @Test
    void shouldCreateNewPerson() {
        Person person = UserFactory.newValidPerson();
        Mockito.when(personRepository.existsById(person.getUsername())).thenReturn(false);
        Mockito.when(personRepository.save(person)).thenReturn(person);

        assertDoesNotThrow(() -> userService.createPerson(person));
    }

    @Test
    void shouldThrowExceptionOnPersonCreationWhenAlreadyExisting() {
        Person person = UserFactory.newValidPerson();
        Mockito.when(personRepository.existsById(person.getUsername())).thenReturn(true);

        assertThrows(UserAlreadyExists.class, () -> userService.createPerson(person));
    }

    @Test
    void createValidBot() {
        Bot bot = UserFactory.newValidBot();
        Mockito.when(botRepository.existsById(bot.getUsername())).thenReturn(false);
        Mockito.when(botRepository.save(bot)).thenReturn(bot);

        assertDoesNotThrow(() -> userService.createBot(bot));
    }

    @Test
    void shouldThrowExceptionOnBotCreationWhenAlreadyExisting() {
        Bot bot = UserFactory.newValidBot();
        Mockito.when(botRepository.existsById(bot.getUsername())).thenReturn(true);

        assertThrows(UserAlreadyExists.class, () -> userService.createBot(bot));
    }

    @TestConfiguration
    static class UserServiceTestContextConfiguration {
        @Bean
        IFUserService ifUserService() {
            return new UserService();
        }
    }
}
