package de.othr.bib48218.chat.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MessageTest {
    @SuppressWarnings("ConstantConditions")
    @Test
    void shouldHaveAuthor() {
        var message = new Message();

        assertThrows(NullPointerException.class, () -> message.setAuthor(null));
        assertThrows(NullPointerException.class, () -> new Message(
            "text",
            new Chat() {
            },
            null,
            LocalDateTime.now()
            ));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void shouldHaveText() {
        var message = new Message();

        assertThrows(NullPointerException.class, () -> message.setText(null));
        assertThrows(NullPointerException.class, () -> new Message(
            null,
            new Chat() {
            },
            new User() {
            },
            LocalDateTime.now()
        ));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void shouldHaveChat() {
        var message = new Message();

        assertThrows(NullPointerException.class, () -> message.setChat(null));
        assertThrows(NullPointerException.class, () -> new Message(
            "text",
            null,
            new User() {
            },
            LocalDateTime.now()
        ));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void shouldHaveTimestamp() {
        var message = new Message();

        assertThrows(NullPointerException.class, () -> message.setTimestamp(null));
        assertThrows(NullPointerException.class, () -> new Message(
            "text",
            new Chat() {
            },
            new User() {
            },
            null
        ));
    }

    @Test
    void shouldHaveNoArgsConstructor() {
        assertDoesNotThrow((ThrowingSupplier<Message>) Message::new);
    }
}
