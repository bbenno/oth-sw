package de.othr.bib48218.chat.controller;

import de.othr.bib48218.chat.entity.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {
    @RequestMapping("/new")
    public ModelAndView showPersonForm() {
        return new ModelAndView("user/new_person", "person", new Person());
    }

    @PostMapping("/new")
    public ModelAndView createPerson(@ModelAttribute("person") Person person) {
        return new ModelAndView("redirect:" + person.getUsername());
    }
}
