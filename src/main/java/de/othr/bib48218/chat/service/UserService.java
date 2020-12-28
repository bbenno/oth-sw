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

import java.util.Optional;

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
        return personRepository.findByFirstName(firstName).get();
    }

    @Override
    public User getUserByUsername(String username) {
        Optional<User> found_person = personRepository.findByUsername(username).map((person) -> person);
        Optional<User> found_bot = botRepository.findByUsername(username).map((bot) -> bot);
        return found_person.orElse(found_bot.orElse(null));
    }

    @Override
    public Person getPersonByUsername(String username) {
        return personRepository.findByUsername(username).get();
    }

    @Override
    public Bot getBotByUsername(String username) {
        return botRepository.findByUsername(username).get();
    }

    @Override
    public Person createPerson(Person person) throws UserAlreadyExists {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        if (personRepository.existsById(person.getUsername()))
            throw new UserAlreadyExists();
        return personRepository.save(person);
    }

    @Override
    public Bot createBot(Bot bot) throws UserAlreadyExists {
        bot.setPassword(passwordEncoder.encode(bot.getPassword()));
        return botRepository.save(bot);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = personRepository.findByUsername(username).get();
        if (user == null)
            throw new UsernameNotFoundException("No User with username '" + username + "'");

        return user;
    }
}
