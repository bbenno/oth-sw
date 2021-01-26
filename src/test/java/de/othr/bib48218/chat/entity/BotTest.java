package de.othr.bib48218.chat.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import de.othr.bib48218.chat.factory.UserFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;

public class BotTest {

    @SuppressWarnings("ConstantConditions")
    @Test
    void shouldHaveUsername() {
        var bot = new Bot();

        assertThrows(NullPointerException.class, () -> bot.setUsername(null));
        assertThrows(NullPointerException.class, () -> new Bot(null));
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

    @Test
    void shouldContainBotInStringRepresentation() {
        Bot bot = UserFactory.newValidBot();

        assertThat(bot.toString()).containsIgnoringCase(Bot.class.getSimpleName());
    }
}
