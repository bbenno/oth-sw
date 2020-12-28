package de.othr.bib48218.chat.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BotTest {
    @SuppressWarnings("ConstantConditions")
    @Test
    void shouldHaveUsername() {
        var bot = new Bot();

        assertThrows(NullPointerException.class, () -> bot.setUsername(null));
        assertThrows(NullPointerException.class, () -> new Bot(null, "password"));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void shouldHavePassword() {
        var bot = new Bot();

        assertThrows(NullPointerException.class, () -> bot.setPassword(null));
        assertThrows(NullPointerException.class, () -> new Bot("username", null));
    }

    @Test
    void shouldHaveNoArgsConstructor() {
        assertDoesNotThrow((ThrowingSupplier<Bot>) Bot::new);
    }

    @Test
    void shouldBeUser() {
        var bot = new Bot();

        assertThat(bot).isInstanceOf(User.class);
    }
}
