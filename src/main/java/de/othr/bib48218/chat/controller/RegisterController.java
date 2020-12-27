package de.othr.bib48218.chat.controller;

import de.othr.bib48218.chat.entity.Person;
import de.othr.bib48218.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private UserService userService;

    @RequestMapping
    public ModelAndView showPersonForm() {
        return new ModelAndView("register", "person", new Person());
    }

    @PostMapping
    public ModelAndView createPerson(@Validated Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new ModelAndView("register", "person", person);
        else {
            userService.createPerson(person);
            return new ModelAndView("redirect:/user/" + person.getUsername());
        }
    }
}
