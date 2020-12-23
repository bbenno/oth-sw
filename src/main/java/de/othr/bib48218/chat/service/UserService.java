package de.othr.bib48218.chat.service;

import de.othr.bib48218.chat.entity.Bot;
import de.othr.bib48218.chat.entity.Person;
import de.othr.bib48218.chat.entity.User;
import de.othr.bib48218.chat.repository.BotRepository;
import de.othr.bib48218.chat.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IFUserService {
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private BotRepository botRepository;

    @Override
    public Person getPersonByFirstName(String firstName) {
        return personRepository.findByFirstName(firstName);
    }

    @Override
    public User getUserByUsername(String username) {
        User found = getPersonByUsername(username);
        return (found != null) ? found : getBotByUsername(username);
    }

    @Override
    public Person getPersonByUsername(String username) {
        return personRepository.findByUsername(username);
    }

    @Override
    public Bot getBotByUsername(String username) {
        return botRepository.findByUsername(username);
    }

    @Override
    public Person createPerson(Person person) {
        return personRepository.save(person);
    }

    @Override
    public Bot createBot(Bot bot) {
        return botRepository.save(bot);
    }
}
