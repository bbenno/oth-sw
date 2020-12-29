package de.othr.bib48218.chat.Validation;

import de.othr.bib48218.chat.entity.Person;
import de.othr.bib48218.chat.factory.UserFactory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PersonValidationTest extends ValidationTest {
    @Test
    void usernameShouldBeNotBlank() {
        Person person = UserFactory.newValidPerson();
        person.setUsername("");

        var violations = validator.validate(person);
        assertThat(violations).isNotEmpty();
    }

    @Test
    void passwordShouldBeNotBlank() {
        Person person = UserFactory.newValidPerson();
        person.setPassword("");

        var violations = validator.validate(person);
        assertThat(violations).isNotEmpty();
    }

    @Test
    void emailShouldHaveValidFormat() {
        Person person = UserFactory.newValidPerson();
        person.setEmail("test");

        var violations = validator.validate(person);
        assertThat(violations).isNotEmpty();
    }
}
