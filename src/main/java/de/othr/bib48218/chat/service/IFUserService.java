package de.othr.bib48218.chat.service;

import de.othr.bib48218.chat.entity.Bot;
import de.othr.bib48218.chat.entity.Person;
import de.othr.bib48218.chat.entity.User;
import de.othr.bib48218.chat.util.UserAlreadyExistsException;
import java.util.Collection;
import java.util.Optional;

public interface IFUserService {

    /* GET User  **********************************************************************************/

    /**
     * Gets the user with a certain username.
     *
     * @param username the string identifying a user
     * @return the user
     */
    Optional<User> getUserByUsername(String username);

    /**
     * Gets the person with a certain username.
     *
     * @param username the string identifying a person
     * @return the person
     */
    Optional<Person> getPersonByUsername(String username);

    /**
     * Gets the bot with a certain username.
     *
     * @param username the string identifying a bot
     * @return the bot
     */
    Optional<Bot> getBotByUsername(String username);

    /**
     * Gets the people with a certain first name.
     *
     * @param firstName the first name identifying the people
     * @return the collection containing the people
     */
    Collection<Person> getPersonByFirstName(String firstName);

    /**
     * Gets the people with a certain last name.
     *
     * @param lastName the last name identifying the people
     * @return the collection containing the people
     */
    Collection<Person> getPersonByLastName(String lastName);

    /**
     * Gets the people with a certain email.
     *
     * @param email the email of the people
     * @return the collection containing the people
     */
    Collection<Person> getPersonByEmail(String email);

    /* GET Users  *********************************************************************************/

    /**
     * Gets all users, including all {@link Person}s and {@link Bot}s.
     *
     * @return the collection of all users
     */
    Collection<User> getAllUsers();

    /**
     * Gets all people.
     *
     * @return the collection of all people
     */
    Collection<Person> getAllPeople();

    /**
     * Gets all bots.
     *
     * @return the collection of all bots
     */
    Collection<Bot> getAllBots();

    /**
     * Gets the users with their username, first name or lastname containing a certain string.
     *
     * @param usernamePattern the string containing the search term
     * @return the collection containing the users with matching name
     */
    Collection<User> getUsersByStringFragment(String usernamePattern);

    /* ADD User  **********************************************************************************/

    /**
     * Saves a certain person.
     *
     * @param person the person
     * @return the saved person
     * @throws UserAlreadyExistsException If a user with identical username is present
     */
    Person createPerson(Person person) throws UserAlreadyExistsException;

    /**
     * Saves a certain bot.
     *
     * @param bot the bot
     * @return the saved bot
     * @throws UserAlreadyExistsException If a user with identical username is present
     */
    Bot createBot(Bot bot) throws UserAlreadyExistsException;

    /* DELETE User  *******************************************************************************/

    /**
     * Deletes the user with a certain username.
     *
     * @param username the sting identifying a user
     */
    void deleteUserByUsername(String username);
}
