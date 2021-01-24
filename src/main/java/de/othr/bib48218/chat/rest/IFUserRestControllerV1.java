package de.othr.bib48218.chat.rest;

import de.othr.bib48218.chat.entity.Bot;
import de.othr.bib48218.chat.entity.Person;
import de.othr.bib48218.chat.entity.User;
import java.util.Collection;
import org.springframework.http.ResponseEntity;

public interface IFUserRestControllerV1 {
    /*
     * CREATE
     */

    /**
     * Creates Person
     *
     * @param person person to create
     * @return created person
     */
    Person createPerson(Person person);

    /**
     * Creates Bot
     *
     * @param bot bot to create
     * @return created bot
     */
    Bot createBot(Bot bot);

    /*
     * READ
     */

    /**
     * Gets all users
     *
     * @return all users
     */
    ResponseEntity<Collection<User>> getUsers();

    /**
     * Gets all persons
     *
     * @return all persons
     */
    ResponseEntity<Collection<Person>> getPersons();

    /**
     * Gets all bots
     *
     * @return bots
     */
    ResponseEntity<Collection<Bot>> getBots();

    /**
     * Gets user by username
     *
     * @param username username, identifying user
     * @return identified user
     */
    ResponseEntity<User> getUser(String username);

    /**
     * Gets person by username
     *
     * @param username username, identifying person
     * @return identified person
     */
    ResponseEntity<Person> getPerson(String username);

    /**
     * Gets bot by username
     *
     * @param username username, identifying bot
     * @return identified bot
     */
    ResponseEntity<Bot> getBot(String username);

    /*
     * UPDATE
     */

    /*
     * DELETE
     */

    /**
     * Deletes user by username
     *
     * @param username username, identifying user
     * @return identified user
     */
    void deleteUser(String username);

    /**
     * Deletes person by username
     *
     * @param username username, identifying person
     * @return identified person
     */
    void deletePerson(String username);

    /**
     * Deletes bot by username
     *
     * @param username username, identifying bot
     * @return identified bot
     */
    void deleteBot(String username);
}
