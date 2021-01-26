package de.othr.bib48218.chat.rest;

import de.othr.bib48218.chat.entity.Bot;
import de.othr.bib48218.chat.entity.Person;
import de.othr.bib48218.chat.entity.User;
import java.util.Collection;
import org.springframework.http.ResponseEntity;

/**
 * A REST controller (v1 of web API) for {@link de.othr.bib48218.chat.entity.Chat}s.
 */
public interface IFUserRestControllerV1 {

    /* CREATE  ************************************************************************************/

    /**
     * Saves a certain person
     *
     * @param person the person to be saved
     * @return the saved person
     */
    Person createPerson(Person person);

    /**
     * Saves a certain bot
     *
     * @param bot the bot to be saved
     * @return the saved bot
     */
    Bot createBot(Bot bot);

    /* READ  **************************************************************************************/

    /**
     * Gets all users, including all {@link Person}s and {@link Bot}s
     *
     * @return the collection containing all users
     */
    ResponseEntity<Collection<User>> getUsers();

    /**
     * Gets all persons.
     *
     * @return the collection containing all persons
     */
    ResponseEntity<Collection<Person>> getPersons();

    /**
     * Gets all bots.
     *
     * @return the collection containing all bots
     */
    ResponseEntity<Collection<Bot>> getBots();

    /**
     * Gets the user with a certain username.
     *
     * @param username the string identifying the user
     * @return the user, or <code>null</code> if no user exists with the username
     */
    ResponseEntity<User> getUser(String username);

    /**
     * Gets the person with a certain username.
     *
     * @param username username, identifying person
     * @return the person, or <code>null</code> if no person exists with the username
     */
    ResponseEntity<Person> getPerson(String username);

    /**
     * Gets the bot with a certain username.
     *
     * @param username username, identifying bot
     * @return the bot, or <code>null</code> if no bot exists with the username
     */
    ResponseEntity<Bot> getBot(String username);

    /* UPDATE  ************************************************************************************/

    /* DELETE  ************************************************************************************/

    /**
     * Deletes the user with a certain username.
     *
     * @param username the string identifying the user
     */
    void deleteUser(String username);

    /**
     * Deletes the person with a certain username.
     *
     * @param username the string identifying the person
     */
    void deletePerson(String username);

    /**
     * Deletes the bot with a certain username.
     *
     * @param username the string identifying the bot
     */
    void deleteBot(String username);

}
