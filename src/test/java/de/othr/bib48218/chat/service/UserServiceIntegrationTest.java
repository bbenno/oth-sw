package de.othr.bib48218.chat.service;

import de.othr.bib48218.chat.entity.Person;
import de.othr.bib48218.chat.repository.BotRepository;
import de.othr.bib48218.chat.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class UserServiceIntegrationTest {
    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    @Autowired
    private UserService userService;
    @MockBean
    private PersonRepository personRepository;
    @MockBean
    private BotRepository botRepository;

    @BeforeEach
    public void setUp() {
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
    public void whenValidName_thenPersonShouldBeFound() {
        String firstName = "Joe";
        Person found = userService.getPersonByFirstName(firstName);

        assertThat(found.getFirstName()).isEqualTo(firstName);
    }

    @TestConfiguration
    static class UserServiceTestContextConfiguration {
        @Bean
        public UserService userService() {
            return new UserService();
        }
    }
}
