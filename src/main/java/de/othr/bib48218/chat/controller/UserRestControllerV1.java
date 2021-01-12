package de.othr.bib48218.chat.controller;

import de.othr.bib48218.chat.util.UserAlreadyExistsException;
import de.othr.bib48218.chat.entity.Bot;
import de.othr.bib48218.chat.entity.Person;
import de.othr.bib48218.chat.entity.User;
import de.othr.bib48218.chat.service.IFUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
        } catch (UserAlreadyExistsException userAlreadyExistsException) {
            return null;
        }
    }

    @PostMapping("bots")
    public Bot createBot(@RequestBody Bot bot) {
        try {
            return userService.createBot(bot);
        } catch (UserAlreadyExistsException userAlreadyExistsException) {
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
    public ResponseEntity<Collection<User>> getUsers() {
        Collection<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(users);
        }
    }

    @GetMapping("users/{username}")
    public ResponseEntity<User> getUser(@PathVariable("username") String username) {
        User user = userService.getUserByUsername(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(user);
        }
    }

    @GetMapping("persons")
    public ResponseEntity<Collection<Person>> getPersons() {
        Collection<Person> persons = userService.getAllPersons();
        if (persons.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(persons);
        }
    }

    @GetMapping("persons/{username}")
    public ResponseEntity<Person> getPerson(@PathVariable("username") String username) {
        Person person = userService.getPersonByUsername(username);
        if (person == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(person);
        }
    }

    @GetMapping("bots")
    public ResponseEntity<Collection<Bot>> getBots() {
        Collection<Bot> bots = userService.getAllBots();
        if (bots.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(bots);
        }
    }

    @GetMapping("bots/{username}")
    public ResponseEntity<Bot> getBot(@PathVariable("username") String username) {
        Bot bot =  userService.getBotByUsername(username);
        if (bot == null) {
            return ResponseEntity.notFound().build();
        } else  {
            return ResponseEntity.ok(bot);
        }
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
