package de.othr.bib48218.chat.repository;

import de.othr.bib48218.chat.entity.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PersonRepositoryIntegrationTest {
    private final Person joe = new Person(
        "joe",
        "",
        "Joe",
        "Smith",
        "joe@smith.com");
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PersonRepository personRepository;

    @BeforeEach
    void setup() {
        entityManager.persist(joe);
        entityManager.flush();
    }

    @Test
    void whenFindByFirstName_thenReturnPerson() {
        Person found = personRepository.findByFirstName(joe.getFirstName());

        assertThat(found).isNotNull();
        assertThat(found.getFirstName()).isEqualTo(joe.getFirstName());
        assertThat(found).isEqualTo(joe);
    }

    @Test
    void whenFindByUsername_thenReturnPerson() {
        Person found = personRepository.findByUsername(joe.getUsername());

        assertThat(found).isNotNull();
        assertThat(found.getUsername()).isEqualTo(joe.getUsername());
        assertThat(found).isEqualTo(joe);
    }
}
