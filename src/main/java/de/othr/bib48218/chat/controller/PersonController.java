package de.othr.bib48218.chat.controller;

import de.othr.bib48218.chat.entity.Person;
import de.othr.bib48218.chat.entity.User;
import de.othr.bib48218.chat.service.IFUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@Scope("session")
public class PersonController {
    @Autowired
    private IFUserService personService;

    @RequestMapping(value = "/person/{username}")
    public String viewPerson(Model model, @PathVariable("username") String username) {
        Optional<User> person = personService.findByUsername(username);
        if (person.isPresent()) {
            model.addAttribute(person);
            return "person";
        } else {
            return "personForm";
        }
    }

    @RequestMapping(value = "/person/new")
    public String viewPersonForm(Model model) {
        return "personForm";
    }

    @PostMapping(value = "/person/new")
    public ModelAndView createPerson(
            ModelMap model,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email
    ) {
        Person person = new Person(username, password, null, firstName, lastName, email);
        person = personService.createPerson(person);
        model.addAttribute("person", person);
        return new ModelAndView("redirect:/person/" + username, model);
    }
}
