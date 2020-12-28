package de.othr.bib48218.chat.repository;

import de.othr.bib48218.chat.entity.Person;

import java.util.Optional;

public interface PersonRepository extends UserRepository<Person> {
    Optional<Person> findByFirstName(String firstName);
}
