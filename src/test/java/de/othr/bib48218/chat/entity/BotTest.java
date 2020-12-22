package de.othr.bib48218.chat.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class BotTest {
    private static final Bot validBot = new Bot("mr_botty", "X7L&tuqN5");

    @Test
    void shouldHaveUsername() {
        var bot = new Bot();

        // noinspection ConstantConditions
        assertThrows(NullPointerException.class, () -> bot.setUsername(null));
    }

    @Test
    void shouldHavePassword() {
        var bot = new Bot();

        // noinspection ConstantConditions
        assertThrows(NullPointerException.class, () -> bot.setPassword(null));
    }
}
