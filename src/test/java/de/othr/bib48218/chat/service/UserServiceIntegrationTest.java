package de.othr.bib48218.chat.service;

import de.othr.bib48218.chat.entity.Person;
import de.othr.bib48218.chat.repository.BotRepository;
import de.othr.bib48218.chat.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserServiceIntegrationTest {
    @Autowired
    private IFUserService userService;
    @MockBean
    private PersonRepository personRepository;
    @MockBean
    private BotRepository botRepository;

    @BeforeEach
    void setUp() {
        Person joe = new Person(
                "joe",
                "",
                null,
                "Joe",
                "Smith",
                "joe@smith.com");

        Mockito.when(personRepository.findByFirstName(joe.getFirstName())).thenReturn(joe);
    }

    @Test
    void whenValidFirstName_thenPersonShouldBeFound() {
        String firstName = "Joe";
        Person found = userService.getPersonByFirstName(firstName);

        assertThat(found).isNotNull();
        assertThat(found.getFirstName()).isEqualTo(firstName);
    }

    @TestConfiguration
    static class UserServiceTestContextConfiguration {
        @Bean
        IFUserService ifUserService() {
            return new UserService();
        }
    }
}
