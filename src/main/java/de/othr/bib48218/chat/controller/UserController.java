package de.othr.bib48218.chat.controller;

import de.othr.bib48218.chat.entity.Person;
import de.othr.bib48218.chat.entity.User;
import de.othr.bib48218.chat.service.IFUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IFUserService userService;

    @RequestMapping("{username}")
    public ModelAndView showUser(@PathVariable String username) {
        Optional<User> found = userService.getUserByUsername(username);
        if (found.isPresent()) {
            return new ModelAndView("user/show", "user", found.get());
        } else {
            return new ModelAndView("redirect:/");
        }
    }
}
