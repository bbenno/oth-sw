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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Service
class UserService implements IFUserService, UserDetailsService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private BotRepository botRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public Collection<Person> getPersonByFirstName(String firstName) {
        return personRepository.findByFirstNameOrderByFirstName(firstName);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Person> getPersonByLastName(String lastName) {
        return personRepository.findByLastNameOrderByLastName(lastName);
    }

    @Override
    public Collection<Person> getPersonByEmail(String email) {
        return personRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserByUsername(String username) {
        return personRepository.findByUsername(username)
            .map(person -> (User) person)
            .or(() -> botRepository.findByUsername(username).map(bot -> (User) bot));
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<User> getUsersByStringFragment(String usernamePattern) {
        return Stream.concat(
            Stream.concat(
                Stream.concat(
                    personRepository.findByUsernameContains(usernamePattern).stream(),
                    botRepository.findByUsernameContains(usernamePattern).stream()
                ),
                Stream.concat(
                    personRepository.findByProfileNameContains(usernamePattern).stream(),
                    botRepository.findByProfileNameContains(usernamePattern).stream()
                )
            ),
            Stream.concat(
                personRepository.findByFirstNameContains(usernamePattern).stream(),
                personRepository.findByLastNameContains(usernamePattern).stream()
            )
        ).distinct()
            .filter(User::isEnabled)
            .collect(Collectors.toUnmodifiableList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Person> getPersonByUsername(String username) {
        return personRepository.findByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return personRepository.findByUsername(username)
            .orElseThrow(
                () -> new UsernameNotFoundException("No User with username '" + username + "'")
            );
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<User> getAllUsers() {
        return Stream.concat(
            getAllPeople().stream(),
            getAllBots().stream()
        ).collect(Collectors.toUnmodifiableList());
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Person> getAllPeople() {
        return StreamSupport.stream(personRepository.findAll().spliterator(), false)
            .collect(Collectors.toUnmodifiableList());
    }

    @Override
    @Transactional(readOnly = true)
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

    private <T extends User> T withEncryptedPassword(T user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;
    }
}
