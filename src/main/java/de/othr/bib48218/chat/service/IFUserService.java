package de.othr.bib48218.chat.service;

import de.othr.bib48218.chat.entity.Person;
import de.othr.bib48218.chat.entity.User;

import java.util.Optional;

public interface IFUserService {
    Optional<User> findByUsername(String username);
    Person createPerson(Person person);
}
