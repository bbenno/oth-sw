package de.othr.bib48218.chat.service;

import de.othr.bib48218.chat.entity.Person;
import de.othr.bib48218.chat.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IFUserService {
    @Autowired
    private PersonRepository personRepository;

    @Override
    public Person getPersonByFirstName(String firstName) {
        return personRepository.findByFirstName(firstName);
    }
}
