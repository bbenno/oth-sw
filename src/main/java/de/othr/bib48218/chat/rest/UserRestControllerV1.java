package de.othr.bib48218.chat.rest;

import de.othr.bib48218.chat.entity.Bot;
import de.othr.bib48218.chat.entity.Person;
import de.othr.bib48218.chat.entity.User;
import de.othr.bib48218.chat.service.IFUserService;
import de.othr.bib48218.chat.util.UserAlreadyExistsException;
import java.util.Collection;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webapi/v1/")
public class UserRestControllerV1 implements IFUserRestControllerV1 {

    @Autowired
    private IFUserService userService;

    /* CREATE  ************************************************************************************/

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

    /* READ  **************************************************************************************/

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
        Optional<User> user = userService.getUserByUsername(username);
        return ResponseEntity.of(user);
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
        Optional<Person> person = userService.getPersonByUsername(username);
        return ResponseEntity.of(person);
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
        Optional<Bot> bot = userService.getBotByUsername(username);
        return ResponseEntity.of(bot);
    }

    /* UPDATE  ************************************************************************************/

    /* DELETE  ************************************************************************************/

    @DeleteMapping("users/{username}")
    public void deleteUser(@PathVariable("username") String username) {
        userService.deleteUserByUsername(username);
    }

    @DeleteMapping("persons/{username}")
    public void deletePerson(@PathVariable("username") String username) {
        userService.deleteUserByUsername(username);
    }

    @DeleteMapping("bots/{username}")
    public void deleteBot(@PathVariable("username") String username) {
        userService.deleteUserByUsername(username);
    }

}
