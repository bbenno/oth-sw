package de.othr.bib48218.chat.validation;

import static org.assertj.core.api.Assertions.assertThat;

import de.othr.bib48218.chat.entity.Chat;
import de.othr.bib48218.chat.entity.Message;
import de.othr.bib48218.chat.entity.User;
import de.othr.bib48218.chat.factory.ChatFactory;
import de.othr.bib48218.chat.factory.MessageFactory;
import de.othr.bib48218.chat.factory.UserFactory;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

public class MessageValidationTest extends ValidationTest {

    private static Chat chat() {
        return ChatFactory.newValidGroupChat();
    }

    private static User author() {
        return UserFactory.newValidPerson();
    }

    @Test
    void textShouldNotBeBlank() {
        String text = "";
        Message message = MessageFactory.newMessageWithText(chat(), author(), text);

        var violations = validator.validate(message);

        assertThat(violations).isNotEmpty();
    }

    @Test
    void timestampShouldNotBeInTheFuture() {
        LocalDateTime timestamp = LocalDateTime.now().plusDays(1);
        Message message = MessageFactory.newMessageAtDateTime(chat(), author(), timestamp);

        var violations = validator.validate(message);

        assertThat(violations).isNotEmpty();
    }
}
