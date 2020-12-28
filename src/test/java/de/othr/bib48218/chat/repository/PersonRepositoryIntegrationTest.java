package de.othr.bib48218.chat.repository;

import de.othr.bib48218.chat.entity.Person;
import de.othr.bib48218.chat.factory.UserFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PersonRepositoryIntegrationTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PersonRepository personRepository;

    @Test
    void whenFindByFirstName_thenReturnPerson() {
        Person person = UserFactory.newValidPerson();
        entityManager.persistAndFlush(person);

        Optional<Person> found = personRepository.findByFirstName(person.getFirstName());

        assertThat(found).isPresent();
        Person foundPerson = found.get();
        assertThat(foundPerson.getFirstName()).isEqualTo(person.getFirstName());
        assertThat(foundPerson).isEqualTo(person);
    }

    @Test
    void whenFindByUsername_thenReturnPerson() {
        Person person = UserFactory.newValidPerson();
        entityManager.persistAndFlush(person);

        Optional<Person> found = personRepository.findByUsername(person.getUsername());

        assertThat(found).isPresent();
        Person foundPerson = found.get();
        assertThat(foundPerson.getUsername()).isEqualTo(person.getUsername());
        assertThat(foundPerson).isEqualTo(person);
    }

    @Test
    void usernameShouldBeUnique() {
        Person person = UserFactory.newValidPerson();
        entityManager.persistAndFlush(person);

        Person otherPerson = UserFactory.newValidPersonWithUsername(person.getUsername());

        assertThrows(Exception.class, () -> entityManager.persist(otherPerson));
    }
}
