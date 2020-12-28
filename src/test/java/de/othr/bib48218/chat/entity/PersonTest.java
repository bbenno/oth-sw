package de.othr.bib48218.chat.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;

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
}
