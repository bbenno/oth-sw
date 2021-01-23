package de.othr.bib48218.chat.repository;

import static org.assertj.core.api.Assertions.assertThat;

import de.othr.bib48218.chat.entity.Person;
import de.othr.bib48218.chat.factory.UserFactory;
import java.util.Collection;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("testing")
class PersonRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PersonRepository personRepository;

    @Test
    void whenFindByFirstName_thenReturnPerson() {
        Person person = UserFactory.newValidPerson();
        entityManager.persistAndFlush(person);

        Collection<Person> found = personRepository
            .findByFirstNameOrderByFirstName(person.getFirstName());

        assertThat(found).isNotNull();
        assertThat(found).isNotEmpty();
        assertThat(found).contains(person);
    }

    @Test
    void whenFindByUsername_thenReturnPerson() {
        Person person = UserFactory.newValidPerson();
        person = entityManager.persistAndFlush(person);

        Optional<Person> found = personRepository.findByUsername(person.getUsername());

        assertThat(found).isPresent();
        Person foundPerson = found.get();
        assertThat(foundPerson.getUsername()).isEqualTo(person.getUsername());
        assertThat(foundPerson).isEqualTo(person);
    }

    @Test
    void saveValidPerson() {
        Person person = UserFactory.newValidPerson();

        Person savedPerson = personRepository.save(person);

        assertThat(savedPerson).isNotNull();
        assertThat(savedPerson).isEqualTo(person);
    }
/*
    @Test
    void usernameShouldBeUnique() {
        Person person = UserFactory.newValidPerson();
        person = entityManager.persistAndFlush(person);

        Person otherPerson = UserFactory.newValidPersonWithUsername(person.getUsername());

        assertThrows(Exception.class, () -> personRepository.save(otherPerson));
    }
 */
}
