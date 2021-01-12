package de.othr.bib48218.chat.controller;

import de.othr.bib48218.chat.entity.Person;
import de.othr.bib48218.chat.entity.User;
import de.othr.bib48218.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("{username}")
    public ModelAndView showUser(@PathVariable String username) {
        Optional<User> found = userService.getUserByUsername(username);
        if (found.isPresent()) {
            Person person = (Person) found.get();
            return new ModelAndView("user/show_person", "person", person);
        } else {
            return new ModelAndView("redirect:new");
        }
    }
}
