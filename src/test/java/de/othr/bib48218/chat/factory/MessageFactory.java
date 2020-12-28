package de.othr.bib48218.chat.factory;

import com.github.javafaker.Faker;
import de.othr.bib48218.chat.entity.Chat;
import de.othr.bib48218.chat.entity.Message;
import de.othr.bib48218.chat.entity.User;

import java.time.LocalDateTime;

public class MessageFactory {
    private static final Faker faker = new Faker();

    public static Message newMessage(Chat chat, User author) {
        String text = faker.lorem().sentence();
        return new Message(text, chat, author, LocalDateTime.now());
    }
}
