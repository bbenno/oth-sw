package de.othr.bib48218.chat.service;

import de.othr.bib48218.chat.entity.Bot;
import de.othr.bib48218.chat.entity.Person;
import de.othr.bib48218.chat.entity.ServiceType;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PartnerUserService implements IFUserService {

    private final ServiceType serviceType = ServiceType.BANK;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private BotRepository botRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public Collection<Person> getPersonByFirstName(String firstName) {
        return filterByPartner(personRepository.findByFirstNameOrderByFirstName(firstName));
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Person> getPersonByLastName(String lastName) {
        return filterByPartner(personRepository.findByLastNameOrderByLastName(lastName));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserByUsername(String username) {
        return personRepository.findByUsername(username)
            .filter(this::isRightScope)
            .map(person -> (User) person)
            .or(
                () -> botRepository.findByUsername(username)
                    .filter(this::isRightScope)
                    .map(bot -> (User) bot)
            );
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
            .filter(this::isRightScope)
            .collect(Collectors.toUnmodifiableList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Person> getPersonByUsername(String username) {
        return personRepository.findByUsername(username).filter(this::isRightScope);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Bot> getBotByUsername(String username) {
        return botRepository.findByUsername(username).filter(this::isRightScope);
    }

    @Override
    @Transactional
    public Person createPerson(Person person) throws UserAlreadyExistsException {
        if (personRepository.existsById(person.getUsername())) {
            throw new UserAlreadyExistsException();
        } else {
            person.setScope(serviceType);
            return personRepository.save(withEncryptedPassword(person));
        }
    }

    @Override
    @Transactional
    public Bot createBot(Bot bot) throws UserAlreadyExistsException {
        if (botRepository.existsById(bot.getUsername())) {
            throw new UserAlreadyExistsException();
        } else {
            bot.setScope(serviceType);
            return botRepository.save(withEncryptedPassword(bot));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<User> getAllUsers() {
        return Stream.concat(
            getAllPersons().stream(),
            getAllBots().stream()
        )
            .collect(Collectors.toUnmodifiableList());
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Person> getAllPersons() {
        return StreamSupport.stream(personRepository.findAll().spliterator(), false)
            .filter(this::isRightScope)
            .collect(Collectors.toUnmodifiableList());
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Bot> getAllBots() {
        return StreamSupport.stream(botRepository.findAll().spliterator(), false)
            .filter(this::isRightScope)
            .collect(Collectors.toUnmodifiableList());
    }

    @Override
    @Transactional
    public void deleteUserByUsername(String username) {
        personRepository.findByUsername(username)
            .filter(this::isRightScope)
            .ifPresent(p -> p.setEnabled(false));
        botRepository.findByUsername(username)
            .filter(this::isRightScope)
            .ifPresent(b -> b.setEnabled(false));
    }

    <T extends User> T withEncryptedPassword(T user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;
    }

    private <TUser extends User> boolean isRightScope(TUser u) {
        return u.getScope() == serviceType;
    }

    private <TUser extends User> Collection<TUser> filterByPartner(Collection<TUser> users) {
        return users.stream().filter(this::isRightScope).collect(Collectors.toUnmodifiableList());
    }
}
