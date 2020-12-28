package de.othr.bib48218.chat.factory;

import com.github.javafaker.Faker;
import de.othr.bib48218.chat.entity.Bot;
import de.othr.bib48218.chat.entity.Person;

public class UserFactory {
    private static final Faker faker = new Faker();

    public static Person newValidPerson() {
        String username = faker.name().username();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String password = password();
        String email = faker.internet().emailAddress();

        return new Person(username, password, firstName, lastName, email);
    }

    public static Bot newValidBot() {
        String username = faker.name().username();
        String password = password();

        return new Bot(username, password);
    }

    public static Bot newValidBotWithUsername(String username) {
        return new Bot(username, password());
    }

    private static String password() {
        return faker.internet().password(8, 20);
    }
}
