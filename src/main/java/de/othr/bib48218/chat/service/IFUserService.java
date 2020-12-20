package de.othr.bib48218.chat.service;

import de.othr.bib48218.chat.entity.Person;

public interface IFUserService {
    Person getPersonByFirstName(String firstName);
}
