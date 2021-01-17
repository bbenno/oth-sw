package de.othr.bib48218.chat.service;

import de.othr.bib48218.chat.entity.Bot;
import de.othr.bib48218.chat.entity.Person;
import de.othr.bib48218.chat.entity.User;
import de.othr.bib48218.chat.repository.BotRepository;
import de.othr.bib48218.chat.repository.PersonRepository;
import de.othr.bib48218.chat.util.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class UserService implements IFUserService, UserDetailsService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private BotRepository botRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Collection<Person> getPersonByFirstName(String firstName) {
        return personRepository.findByFirstName(firstName);
    }

    @Override
    public Collection<Person> getPersonByLastName(String lastName) {
        return personRepository.findByLastName(lastName);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        Optional<User> found = personRepository.findByUsername(username).map((person) -> person);
        if (found.isEmpty()) {
            found = botRepository.findByUsername(username).map((bot) -> bot);
        }
        return found;
    }

    @Override
    public Optional<Person> getPersonByUsername(String username) {
        return personRepository.findByUsername(username);
    }

    @Override
    public Optional<Bot> getBotByUsername(String username) {
        return botRepository.findByUsername(username);
    }

    @Override
    public Person createPerson(Person person) throws UserAlreadyExistsException {
        if (personRepository.existsById(person.getUsername())) {
            throw new UserAlreadyExistsException();
        } else {
            person.setPassword(passwordEncoder.encode(person.getPassword()));
            return personRepository.save(person);
        }
    }

    @Override
    public Bot createBot(Bot bot) throws UserAlreadyExistsException {
        if (botRepository.existsById(bot.getUsername())) {
            throw new UserAlreadyExistsException();
        } else {
            bot.setPassword(passwordEncoder.encode(bot.getPassword()));
            return botRepository.save(bot);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> user = personRepository.findByUsername(username);

        return user.orElseThrow(() -> new UsernameNotFoundException("No User with username '" + username + "'"));
    }

    @Override
    public Collection<User> getAllUsers() {
        Stream<User> personStream = StreamSupport.stream(personRepository.findAll().spliterator(), false).map((person) -> person);
        Stream<User> botStream = StreamSupport.stream(botRepository.findAll().spliterator(), false).map((bot) -> bot);

        return Stream.concat(personStream, botStream).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Collection<Person> getAllPersons() {
        Stream<Person> personStream = StreamSupport.stream(personRepository.findAll().spliterator(), false);

        return personStream.collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Collection<Bot> getAllBots() {
        Stream<Bot> botStream = StreamSupport.stream(botRepository.findAll().spliterator(), false);

        return botStream.collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void deleteUserByUsername(String username) {
        deletePersonByUsername(username);
        deleteBotByUsername(username);
    }

    @Override
    public void deletePersonByUsername(String username) {
        personRepository.deleteById(username);
    }

    @Override
    public void deleteBotByUsername(String username) {
        botRepository.deleteById(username);
    }

    @Override
    public void saveUser(User user) {
        if (user.getClass().equals(Person.class))
            personRepository.save((Person) user);
        else if (user.getClass().equals(Bot.class))
            botRepository.save((Bot) user);
    }
}
