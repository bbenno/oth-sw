package de.othr.bib48218.chat.controller;

import de.othr.bib48218.chat.UserAlreadyExists;
import de.othr.bib48218.chat.entity.Bot;
import de.othr.bib48218.chat.entity.Person;
import de.othr.bib48218.chat.entity.User;
import de.othr.bib48218.chat.service.IFUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/webapi/v1/")
public class UserRestControllerV1 implements IFUserRestControllerV1 {
    @Autowired
    private IFUserService userService;

    /*
     * CREATE
     */

    @PostMapping("persons")
    public Person createPerson(@RequestBody Person person) {
        try {
            return userService.createPerson(person);
        } catch (UserAlreadyExists userAlreadyExists) {
            return null;
        }
    }

    @PostMapping("bots")
    public Bot createBot(@RequestBody Bot bot) {
        try {
            return userService.createBot(bot);
        } catch (UserAlreadyExists userAlreadyExists) {
            return null;
        }
    }

    /*
     * UPDATE
     */


    /*
     * READ
     */

    @GetMapping("users")
    public Collection<User> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("users/{username}")
    public User getUser(@PathVariable("username") String username) {
        return userService.getUserByUsername(username);
    }

    @GetMapping("persons")
    public Collection<Person> getPersons() {
        return userService.getAllPersons();
    }

    @GetMapping("persons/{username}")
    public Person getPerson(@PathVariable("username") String username) {
        return userService.getPersonByUsername(username);
    }

    @GetMapping("bots")
    public Collection<Bot> getBots() {
        return userService.getAllBots();
    }

    @GetMapping("bots/{username}")
    public Bot getBot(@PathVariable("username") String username) {
        return userService.getBotByUsername(username);
    }

    /*
     * DELETE
     */

    @DeleteMapping("users/{username}")
    public void deleteUser(@PathVariable("username") String username) {
        userService.deleteUserByUsername(username);
    }

    @DeleteMapping("persons/{username}")
    public void deletePerson(@PathVariable("username") String username) {
        userService.deletePersonByUsername(username);
    }

    @DeleteMapping("bots/{username}")
    public void deleteBot(@PathVariable("username") String username) {
        userService.deleteBotByUsername(username);
    }
}
