package de.othr.bib48218.chat.controller;

import de.othr.bib48218.chat.UserAlreadyExistsException;
import de.othr.bib48218.chat.entity.Person;
import de.othr.bib48218.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private UserService userService;

    @RequestMapping
    public ModelAndView showPersonForm() {
        return new ModelAndView("register", "person", new Person());
    }

    @PostMapping
    public ModelAndView createPerson(@Validated Person person, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors())
            return new ModelAndView("register", "person", person);
        else {
            try {
                userService.createPerson(person);
                return new ModelAndView("redirect:/user/" + person.getUsername());
            } catch (UserAlreadyExistsException userAlreadyExistsException) {
                var notUniqueError = new FieldError(bindingResult.getObjectName(), "username", "Username exists already");
                bindingResult.addError(notUniqueError);
                return new ModelAndView("register", "person", person);
            }
        }
    }

    @InitBinder
    void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }
}
