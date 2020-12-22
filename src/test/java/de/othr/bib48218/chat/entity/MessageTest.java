package de.othr.bib48218.chat.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class MessageTest {
    @Test
    void shouldHaveText() {
        assertThrows(NullPointerException.class, () -> new Message(
            null,
            LocalDateTime.now(),
            null,
            new User() {
            },
            null
        ));
    }

    @Test
    void shouldHaveTimeStamp() {
        assertThrows(NullPointerException.class, () -> new Message(
            "text",
            null,
            null,
            new User() {
            },
            null
        ));
    }

    @Test
    void shouldHaveAuthor() {
        assertThrows(NullPointerException.class, () -> new Message(
            "text",
            LocalDateTime.now(),
            null,
            null,
            null
        ));
    }
}
