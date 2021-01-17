package de.othr.bib48218.chat.repository;

import de.othr.bib48218.chat.entity.Person;

import java.util.List;

public interface PersonRepository extends UserRepository<Person> {
    List<Person> findByFirstNameOrderByFirstName(String firstName);

    List<Person> findByLastNameOrderByLastName(String lastName);
}
