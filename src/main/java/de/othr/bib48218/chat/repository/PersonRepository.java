package de.othr.bib48218.chat.repository;

import de.othr.bib48218.chat.entity.Person;
import java.util.Collection;
import java.util.List;

/**
 * CRUD repository of persons.
 */
public interface PersonRepository extends UserRepository<Person> {

    List<Person> findByFirstNameOrderByFirstName(String firstName);

    Collection<Person> findByFirstNameContains(String firstNameFragment);

    List<Person> findByLastNameOrderByLastName(String lastName);

    Collection<Person> findByLastNameContains(String lastNameFragment);

    Collection<Person> findByEmail(String email);

}
