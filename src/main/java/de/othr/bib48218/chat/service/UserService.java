package de.othr.bib48218.chat.service;

import de.othr.bib48218.chat.UserAlreadyExists;
import de.othr.bib48218.chat.entity.Bot;
import de.othr.bib48218.chat.entity.Person;
import de.othr.bib48218.chat.entity.User;
import de.othr.bib48218.chat.repository.BotRepository;
import de.othr.bib48218.chat.repository.PersonRepository;
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
    public Person getPersonByFirstName(String firstName) {
        return personRepository.findByFirstName(firstName).orElse(null);
    }

    @Override
    public User getUserByUsername(String username) {
        Optional<User> found_person = personRepository.findByUsername(username).map((person) -> person);
        Optional<User> found_bot = botRepository.findByUsername(username).map((bot) -> bot);
        return found_person.orElse(found_bot.orElse(null));
    }

    @Override
    public Person getPersonByUsername(String username) {
        return personRepository.findByUsername(username).orElse(null);
    }

    @Override
    public Bot getBotByUsername(String username) {
        return botRepository.findByUsername(username).orElse(null);
    }

    @Override
    public Person createPerson(Person person) throws UserAlreadyExists {
        if (personRepository.existsById(person.getUsername())) {
            throw new UserAlreadyExists();
        } else {
            person.setPassword(passwordEncoder.encode(person.getPassword()));
            return personRepository.save(person);
        }
    }

    @Override
    public Bot createBot(Bot bot) throws UserAlreadyExists {
        if (botRepository.existsById(bot.getUsername())) {
            throw new UserAlreadyExists();
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
    public Collection<User> getAll() {
        Stream<User> personStream = StreamSupport.stream(personRepository.findAll().spliterator(), false).map((person) -> person);
        Stream<User> botStream = StreamSupport.stream(botRepository.findAll().spliterator(), false).map((bot) -> bot);
        return Stream.concat(personStream, botStream).collect(Collectors.toUnmodifiableList());
    }
}
