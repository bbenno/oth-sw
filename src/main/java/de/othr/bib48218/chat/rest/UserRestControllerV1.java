package de.othr.bib48218.chat.rest;

import de.othr.bib48218.chat.entity.Bot;
import de.othr.bib48218.chat.entity.PeerChat;
import de.othr.bib48218.chat.entity.Person;
import de.othr.bib48218.chat.entity.User;
import de.othr.bib48218.chat.service.IFChatService;
import de.othr.bib48218.chat.service.IFUserService;
import de.othr.bib48218.chat.util.UserAlreadyExistsException;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@RestController
@RequestMapping("/webapi/v1")
class UserRestControllerV1 implements IFUserRestControllerV1 {

    @Autowired
    @Qualifier("bankUserService")
    private IFUserService userService;

    @Autowired
    private IFChatService chatService;

    /* CREATE  ************************************************************************************/

    @Override
    @PostMapping("/people")
    public Person createPerson(@RequestBody Person person) {
        try {
            return userService.createPerson(person);
        } catch (UserAlreadyExistsException userAlreadyExistsException) {
            return null;
        }
    }

    @Override
    @PostMapping("/bots")
    public Bot createBot(@RequestBody Bot bot) {
        try {
            return userService.createBot(bot);
        } catch (UserAlreadyExistsException userAlreadyExistsException) {
            return null;
        }
    }

    /* READ  **************************************************************************************/

    @Override
    @GetMapping("/users")
    public ResponseEntity<Collection<User>> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Override
    @GetMapping("/users/{username}")
    public ResponseEntity<User> getUser(@PathVariable("username") String username) {
        return ResponseEntity.of(userService.getUserByUsername(username));
    }

    @Override
    @GetMapping("/people")
    public ResponseEntity<Collection<Person>> getPeople() {
        return ResponseEntity.ok(userService.getAllPeople());
    }

    @Override
    @GetMapping("/people/{username}")
    public ResponseEntity<Person> getPerson(@PathVariable("username") String username) {
        return ResponseEntity.of(userService.getPersonByUsername(username));
    }

    @GetMapping("/people/{username}/chat")
    public PeerChat getPersonChat(@PathVariable("username") String username) {
        Bot bankServiceBot = userService.getBotByUsername("bank_service").get();
        Person person = userService.getPersonByUsername(username).orElseGet(
            () -> {
                var p = new Person(username, "not set");
                try {
                    return userService.createPerson(p);
                } catch (UserAlreadyExistsException e) {
                }
                return p;
            });

        return chatService.getOrCreatePeerChatOf(bankServiceBot, person);
    }

    @Override
    @GetMapping("/bots")
    public ResponseEntity<Collection<Bot>> getBots() {
        Collection<Bot> bots = userService.getAllBots();
        if (bots.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(bots);
        }
    }

    @Override
    @GetMapping("/bots/{username}")
    public ResponseEntity<Bot> getBot(@PathVariable("username") String username) {
        return ResponseEntity.of(userService.getBotByUsername(username));
    }

    /* UPDATE  ************************************************************************************/

    /* DELETE  ************************************************************************************/

    @Override
    @DeleteMapping("/users/{username}")
    public void deleteUser(@PathVariable("username") String username) {
        userService.deleteUserByUsername(username);
    }

    @Override
    @DeleteMapping("/people/{username}")
    public void deletePerson(@PathVariable("username") String username) {
        userService.deleteUserByUsername(username);
    }

    @Override
    @DeleteMapping("/bots/{username}")
    public void deleteBot(@PathVariable("username") String username) {
        userService.deleteUserByUsername(username);
    }

}
