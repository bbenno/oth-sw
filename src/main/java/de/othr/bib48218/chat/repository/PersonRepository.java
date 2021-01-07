package de.othr.bib48218.chat.repository;

import de.othr.bib48218.chat.entity.Person;

import java.util.Collection;

public interface PersonRepository extends UserRepository<Person> {
    Collection<Person> findByFirstName(String firstName);

    Collection<Person> findByLastName(String lastName);
}
