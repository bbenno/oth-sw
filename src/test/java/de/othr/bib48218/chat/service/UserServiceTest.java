package de.othr.bib48218.chat.service;

import de.othr.bib48218.chat.util.UserAlreadyExistsException;
import de.othr.bib48218.chat.entity.Bot;
import de.othr.bib48218.chat.entity.Person;
import de.othr.bib48218.chat.entity.User;
import de.othr.bib48218.chat.factory.UserFactory;
import de.othr.bib48218.chat.repository.BotRepository;
import de.othr.bib48218.chat.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private BotRepository botRepository;

    @Spy
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    void shouldGetPersonByFirstNameIfExisting() {
        Person person = UserFactory.newValidPerson();
        String firstName = person.getFirstName();
        var persons = new ArrayList<Person>();
        persons.add(person);
        when(personRepository.findByFirstName(firstName)).thenReturn(persons);

        Collection<Person> found = userService.getPersonByFirstName(firstName);

        assertThat(found).isNotNull();
        assertThat(found).isNotEmpty();
        assertThat(found).contains(person);
    }

    @Test
    void shouldGetBotByUsernameIfExisting() {
        Bot bot = UserFactory.newValidBot();
        String username = bot.getUsername();
        when(botRepository.findByUsername(username)).thenReturn(Optional.of(bot));

        Bot found = userService.getBotByUsername(username);

        assertThat(found).isNotNull();
        assertThat(found.getUsername()).isEqualTo(username);
        assertThat(found).isEqualTo(bot);
    }

    @Test
    void shouldGetPersonByUsernameIfExisting() {
        Person person = UserFactory.newValidPerson();
        String username = person.getUsername();
        when(personRepository.findByUsername(username)).thenReturn(Optional.of(person));

        Person found = userService.getPersonByUsername(username);

        assertThat(found).isNotNull();
        assertThat(found.getUsername()).isEqualTo(username);
        assertThat(found).isEqualTo(person);
    }

    @Test
    void shouldGetUserByUsernameIfPersonExisting() {
        Person user = UserFactory.newValidPerson();
        String username = user.getUsername();
        when(botRepository.findByUsername(username)).thenReturn(Optional.empty());
        when(personRepository.findByUsername(username)).thenReturn(Optional.of(user));

        User found = userService.getUserByUsername(username);

        assertThat(found).isNotNull();
        assertThat(found.getUsername()).isEqualTo(username);
        assertThat(found).isEqualTo(user);
    }

    @Test
    void shouldGetUserByUsernameIfBotExisting() {
        Bot user = UserFactory.newValidBot();
        String username = user.getUsername();
        when(botRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(personRepository.findByUsername(username)).thenReturn(Optional.empty());

        User found = userService.getUserByUsername(username);

        assertThat(found).isNotNull();
        assertThat(found.getUsername()).isEqualTo(username);
        assertThat(found).isEqualTo(user);
    }

    @Test
    void shouldCreateNewPerson() {
        Person person = UserFactory.newValidPerson();
        when(personRepository.existsById(person.getUsername())).thenReturn(false);
        when(personRepository.save(person)).thenReturn(person);

        assertDoesNotThrow(() -> userService.createPerson(person));
    }

    @Test
    void shouldThrowExceptionOnPersonCreationWhenAlreadyExisting() {
        Person person = UserFactory.newValidPerson();
        when(personRepository.existsById(person.getUsername())).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> userService.createPerson(person));
    }

    @Test
    void createValidBot() {
        Bot bot = UserFactory.newValidBot();
        when(botRepository.existsById(bot.getUsername())).thenReturn(false);
        when(botRepository.save(bot)).thenReturn(bot);

        assertDoesNotThrow(() -> userService.createBot(bot));
    }

    @Test
    void shouldThrowExceptionOnBotCreationWhenAlreadyExisting() {
        Bot bot = UserFactory.newValidBot();
        when(botRepository.existsById(bot.getUsername())).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> userService.createBot(bot));
    }

}
