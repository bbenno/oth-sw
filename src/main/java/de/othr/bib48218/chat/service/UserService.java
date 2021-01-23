package de.othr.bib48218.chat.service;

import de.othr.bib48218.chat.entity.Bot;
import de.othr.bib48218.chat.entity.Person;
import de.othr.bib48218.chat.entity.User;
import de.othr.bib48218.chat.repository.BotRepository;
import de.othr.bib48218.chat.repository.PersonRepository;
import de.othr.bib48218.chat.util.UserAlreadyExistsException;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements IFUserService, UserDetailsService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private BotRepository botRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Collection<Person> getPersonByFirstName(String firstName) {
        return personRepository.findByFirstNameOrderByFirstName(firstName);
    }

    @Override
    @Transactional
    public Collection<Person> getPersonByLastName(String lastName) {
        return personRepository.findByLastNameOrderByLastName(lastName);
    }

    @Override
    @Transactional
    public Optional<User> getUserByUsername(String username) {
        Optional<User> found = personRepository.findByUsername(username).map((person) -> person);
        if (found.isEmpty()) {
            found = botRepository.findByUsername(username).map((bot) -> bot);
        }
        return found;
    }

    @Override
    @Transactional
    public Optional<Person> getPersonByUsername(String username) {
        return personRepository.findByUsername(username);
    }

    @Override
    @Transactional
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
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return personRepository.findByUsername(username)
            .orElseThrow(
                () -> new UsernameNotFoundException("No User with username '" + username + "'")
            );
    }

    @Override
    @Transactional
    public Collection<User> getAllUsers() {
        return Stream.concat(
            StreamSupport.stream(personRepository.findAll().spliterator(), false),
            StreamSupport.stream(botRepository.findAll().spliterator(), false)
        ).collect(Collectors.toUnmodifiableList());
    }

    @Override
    @Transactional
    public Collection<Person> getAllPersons() {
        return StreamSupport.stream(personRepository.findAll().spliterator(), false)
            .collect(Collectors.toUnmodifiableList());
    }

    @Override
    @Transactional
    public Collection<Bot> getAllBots() {
        return StreamSupport.stream(botRepository.findAll().spliterator(), false)
            .collect(Collectors.toUnmodifiableList());
    }

    @Override
    @Transactional
    public void deleteUserByUsername(String username) {
        personRepository.findByUsername(username).ifPresent(p -> p.setEnabled(false));
        botRepository.findByUsername(username).ifPresent(b -> b.setEnabled(false));
    }

    @Override
    @Transactional
    public void updateUser(@NonNull User user) {
        if (user.getClass().equals(Person.class)) {
            updateUser((Person) user);
        } else if (user.getClass().equals(Bot.class)) {
            updateUser((Bot) user);
        }
    }

    @Transactional
    public void updateUser(@NonNull Person person) {
        personRepository.findByUsername(person.getUsername()).ifPresent(p -> {
            p.setEmail(person.getEmail());
            p.setFirstName(person.getFirstName());
            p.setLastName(person.getLastName());
            p.setPassword(person.getPassword());
            p = withEncryptedPassword(p);
        });
    }

    public void updateUser(@NonNull Bot bot) {
        botRepository.findByUsername(bot.getUsername()).ifPresent(b -> {
            b.setPassword(bot.getPassword());
            b = withEncryptedPassword(b);
        });
    }

    private <T extends User> T withEncryptedPassword(T user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;
    }
}
