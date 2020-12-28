package de.othr.bib48218.chat.factory;

import com.github.javafaker.Faker;
import de.othr.bib48218.chat.entity.Person;

public class PersonFactory {
    private static Faker faker = new Faker();

    public static Person newValidPerson() {
        String username = faker.name().username();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String password = faker.internet().password();
        String email = faker.internet().emailAddress();

        return new Person(username, password, firstName, lastName, email);
    }
}
