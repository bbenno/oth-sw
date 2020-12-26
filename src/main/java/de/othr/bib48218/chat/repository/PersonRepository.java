package de.othr.bib48218.chat.repository;

import de.othr.bib48218.chat.entity.Person;

import java.util.Optional;

public interface PersonRepository extends UserRepository<Person> {
    @Override
    Optional<Person> findById(String id);

    Person findByFirstName(String firstName);

    Person findByUsername(String username);
}
