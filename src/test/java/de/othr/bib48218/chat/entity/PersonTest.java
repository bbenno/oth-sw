package de.othr.bib48218.chat.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class PersonTest {
    @Test
    void shouldHaveUsername() {
        var person = new Person();

        assertThrows(NullPointerException.class, () -> person.setUsername(null));
    }

    @Test
    void shouldHavePassword() {
        var person = new Person();

        assertThrows(NullPointerException.class, () -> person.setPassword(null));
    }
}
