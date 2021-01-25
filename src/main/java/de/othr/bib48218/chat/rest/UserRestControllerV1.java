package de.othr.bib48218.chat.rest;

import de.othr.bib48218.chat.entity.Bot;
import de.othr.bib48218.chat.entity.Person;
import de.othr.bib48218.chat.entity.User;
import de.othr.bib48218.chat.service.IFUserService;
import de.othr.bib48218.chat.util.UserAlreadyExistsException;
import java.util.Collection;
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

    @Override
    @PostMapping("persons")
    public Person createPerson(@RequestBody Person person) {
        try {
            return userService.createPerson(person);
        } catch (UserAlreadyExistsException userAlreadyExistsException) {
            return null;
        }
    }

    @Override
    @PostMapping("bots")
    public Bot createBot(@RequestBody Bot bot) {
        try {
            return userService.createBot(bot);
        } catch (UserAlreadyExistsException userAlreadyExistsException) {
            return null;
        }
    }

    /* READ  **************************************************************************************/

    @Override
    @GetMapping("users")
    public ResponseEntity<Collection<User>> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Override
    @GetMapping("users/{username}")
    public ResponseEntity<User> getUser(@PathVariable("username") String username) {
        return ResponseEntity.of(userService.getUserByUsername(username));
    }

    @Override
    @GetMapping("persons")
    public ResponseEntity<Collection<Person>> getPersons() {
        return ResponseEntity.ok(userService.getAllPersons());
    }

    @Override
    @GetMapping("persons/{username}")
    public ResponseEntity<Person> getPerson(@PathVariable("username") String username) {
        return ResponseEntity.of(userService.getPersonByUsername(username));
    }

    @Override
    @GetMapping("bots")
    public ResponseEntity<Collection<Bot>> getBots() {
        Collection<Bot> bots = userService.getAllBots();
        if (bots.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(bots);
        }
    }

    @Override
    @GetMapping("bots/{username}")
    public ResponseEntity<Bot> getBot(@PathVariable("username") String username) {
        return ResponseEntity.of(userService.getBotByUsername(username));
    }

    /* UPDATE  ************************************************************************************/

    /* DELETE  ************************************************************************************/

    @Override
    @DeleteMapping("users/{username}")
    public void deleteUser(@PathVariable("username") String username) {
        userService.deleteUserByUsername(username);
    }

    @Override
    @DeleteMapping("persons/{username}")
    public void deletePerson(@PathVariable("username") String username) {
        userService.deleteUserByUsername(username);
    }

    @Override
    @DeleteMapping("bots/{username}")
    public void deleteBot(@PathVariable("username") String username) {
        userService.deleteUserByUsername(username);
    }

}