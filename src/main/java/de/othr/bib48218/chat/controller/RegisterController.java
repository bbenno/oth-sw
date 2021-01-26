package de.othr.bib48218.chat.controller;

import de.othr.bib48218.chat.entity.Person;
import de.othr.bib48218.chat.service.IFUserService;
import de.othr.bib48218.chat.util.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    @Qualifier("userService")
    private IFUserService userService;

    @RequestMapping
    public String showPersonForm(Model model) {
        model.addAttribute("user", new Person());
        return "register";
    }

    @PostMapping
    public String createPerson(
        @Validated Person person,
        BindingResult bindingResult,
        Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", person);
            return "register";
        }

        try {
            person = userService.createPerson(person);
            return "redirect:/user/" + person.getUsername();
        } catch (UserAlreadyExistsException userAlreadyExistsException) {
            bindingResult.addError(new FieldError(
                bindingResult.getObjectName(),
                "username",
                "Username exists already"
            ));

            model.addAttribute("user", person);
            return "register";
        }
    }

    @InitBinder
    void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }
}
