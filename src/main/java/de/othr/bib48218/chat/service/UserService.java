package de.othr.bib48218.chat.service;

import de.othr.bib48218.chat.entity.Bot;
import de.othr.bib48218.chat.entity.Person;
import de.othr.bib48218.chat.entity.User;
import de.othr.bib48218.chat.repository.BotRepository;
import de.othr.bib48218.chat.repository.PersonRepository;
import de.othr.bib48218.chat.util.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return personRepository.findByFirstNameOrderByFirstName(firstName);
    }

    @Override
    public Collection<Person> getPersonByLastName(String lastName) {
        return personRepository.findByLastNameOrderByLastName(lastName);
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
    @Transactional
    public Person createPerson(Person person) throws UserAlreadyExistsException {
        if (personRepository.existsById(person.getUsername())) {
            throw new UserAlreadyExistsException();
        } else {
            return personRepository.save(withEncryptedPassword(person));
        }
    }

    @Override
    @Transactional
    public Bot createBot(Bot bot) throws UserAlreadyExistsException {
        if (botRepository.existsById(bot.getUsername())) {
            throw new UserAlreadyExistsException();
        } else {
            return botRepository.save(withEncryptedPassword(bot));
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
        if (personRepository.existsById(username)) {
            personRepository.deleteById(username);
        } else if (botRepository.existsById(username)) {
            botRepository.deleteById(username);
        }
    }

    @Override
    public void updateUser(@NonNull User user) {
        if (user.getClass().equals(Person.class)) {
            updateUser((Person) user);
        } else if (user.getClass().equals(Bot.class)) {
            updateUser((Bot) user);
        }
    }

    public void updateUser(@NonNull Person person) {
        Optional<Person> found = personRepository.findByUsername(person.getUsername());

        if (found.isPresent()) {
            Person p = found.get();

            p.setEmail(person.getEmail());
            p.setFirstName(person.getFirstName());
            p.setLastName(person.getLastName());
            p.setPassword(person.getPassword());
            p = withEncryptedPassword(p);
        }
    }

    public void updateUser(@NonNull Bot bot) {
        Optional<Bot> found = botRepository.findByUsername(bot.getUsername());

        if (found.isPresent()) {
            Bot b = found.get();

            b.setPassword(bot.getPassword());
            b = withEncryptedPassword(b);
        }
    }

    private <T extends User> T withEncryptedPassword(T user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;
    }
}
