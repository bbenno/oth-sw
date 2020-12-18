package de.othr.bib48218.chat.service;

import de.othr.bib48218.chat.entity.User;
import de.othr.bib48218.chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IFUserService {
    @Qualifier("personRepository")
    @Autowired
    private UserRepository personRepository;

    @Qualifier("botRepository")
    @Autowired
    private UserRepository botRepository;

    @Override
    public Optional<User> findByUsername(String username) {
        Optional<User> found_person;
        found_person = personRepository.findById(username);

        return (found_person.isPresent()) ? found_person : botRepository.findById(username);
    }
}
