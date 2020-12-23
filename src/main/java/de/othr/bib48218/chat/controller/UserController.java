package de.othr.bib48218.chat.controller;

import de.othr.bib48218.chat.entity.Person;
import de.othr.bib48218.chat.entity.User;
import de.othr.bib48218.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/new")
    public ModelAndView showPersonForm() {
        return new ModelAndView("user/new_person", "person", new Person());
    }

    @PostMapping("/new")
    public ModelAndView createPerson(@Validated Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new ModelAndView("user/new_person", "person", person);
        else {
            userService.createPerson(person);
            return new ModelAndView("redirect:" + person.getUsername());
        }
    }

    @RequestMapping("{username}")
    public ModelAndView showUser(@PathVariable String username) {
        User found = userService.getUserByUsername(username);
        if (found != null && found instanceof Person) {
            Person person = (Person) found;
            return new ModelAndView("user/show_person", "person", found);
        } else {
            return new ModelAndView("redirect:new");
        }
    }
}
