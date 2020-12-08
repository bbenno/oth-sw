package de.othr.bib48218.chat.service;

import de.othr.bib48218.chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IFUserService {
    @Qualifier("personRepository")
    @Autowired
    private UserRepository repository;
}
