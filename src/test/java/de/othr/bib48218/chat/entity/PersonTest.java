package de.othr.bib48218.chat.entity;

import com.github.javafaker.Faker;
import de.othr.bib48218.chat.factory.UserFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PersonTest {

    @SuppressWarnings("ConstantConditions")
    @Test
    void shouldHaveUsername() {
        var person = new Person();

        assertThrows(NullPointerException.class, () -> person.setUsername(null));
        assertThrows(NullPointerException.class, () -> new Person(null, "password"));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void shouldHavePassword() {
        var person = new Person();

        assertThrows(NullPointerException.class, () -> person.setPassword(null));
        assertThrows(NullPointerException.class, () -> new Person("username", null));
    }

    @Test
    void shouldHaveNoArgsConstructor() {
        assertDoesNotThrow((ThrowingSupplier<Person>) Person::new);
    }

    @Test
    void firstNameShouldBePartOfStringRepresentationIfPresent() {
        Person person = UserFactory.newValidPerson();
        String firstName = Faker.instance().name().firstName();
        person.setFirstName(firstName);
        person.getProfile().setName("");

        String person_string = person.toString();

        assertThat(person_string).contains(firstName);
    }

    @Test
    void lastNameShouldBePartOfStringRepresentationIfPresent() {
        Person person = UserFactory.newValidPerson();
        String lastName = Faker.instance().name().lastName();
        person.setLastName(lastName);
        person.getProfile().setName("");

        String person_string = person.toString();

        assertThat(person_string).contains(lastName);
    }

    @Test
    void usernameShouldBeStringRepresentationIfNoFirstNameOrLastNamePresent() {
        String username = Faker.instance().name().username();
        Person person = UserFactory.newValidPersonWithUsername(username);
        person.setLastName(null);
        person.setFirstName(null);
        person.getProfile().setName("");

        String person_string = person.toString();

        assertThat(person_string).isEqualTo(username);
    }

    @Test
    void usernameShouldNotBePartOfStringRepresentationIfFirstNamePresent() {
        String username = Faker.instance().name().username();
        String firstName = Faker.instance().name().firstName();
        Person person = UserFactory.newValidPersonWithUsername(username);
        person.setFirstName(firstName);
        person.getProfile().setName("");

        String person_string = person.toString();

        assertThat(person_string).doesNotContain(username);
    }

    @Test
    void usernameShouldNotBePartOfStringRepresentationIfLastNamePresent() {
        String username = Faker.instance().name().username();
        String lastName = Faker.instance().name().firstName();
        Person person = UserFactory.newValidPersonWithUsername(username);
        person.setLastName(lastName);
        person.getProfile().setName("");

        String person_string = person.toString();

        assertThat(person_string).doesNotContain(username);
    }
}
