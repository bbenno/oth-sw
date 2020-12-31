package de.othr.bib48218.chat.service;

import de.othr.bib48218.chat.UserAlreadyExists;
import de.othr.bib48218.chat.entity.Bot;
import de.othr.bib48218.chat.entity.Person;
import de.othr.bib48218.chat.entity.User;

import java.util.Collection;

public interface IFUserService {
    Person getPersonByFirstName(String firstName);

    User getUserByUsername(String username);

    Person getPersonByUsername(String username);

    Bot getBotByUsername(String username);

    Person createPerson(Person person) throws UserAlreadyExists;

    Bot createBot(Bot bot) throws UserAlreadyExists;

    Collection<User> getAll();
}
