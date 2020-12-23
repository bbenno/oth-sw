package de.othr.bib48218.chat.service;

import de.othr.bib48218.chat.entity.Bot;
import de.othr.bib48218.chat.entity.Person;
import de.othr.bib48218.chat.entity.User;

public interface IFUserService {
    Person getPersonByFirstName(String firstName);
    User getUserByUsername(String username);
    Person getPersonByUsername(String username);
    Bot getBotByUsername(String username);
    Person createPerson(Person person);
    Bot createBot(Bot bot);
}
