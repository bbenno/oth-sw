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
    public ModelAndView showUser(@PathVariable String username, Model model) {
        Optional<User> found = userService.getUserByUsername(username);
        if (found.isPresent()) {
            model.addAttribute("user", found.get());
            model.addAttribute("isPerson", found.get().getClass().equals(Person.class));
            return new ModelAndView("user/show", model.asMap());
        } else {
            return new ModelAndView("redirect:/");
        }
    }

    @RequestMapping("/{username}/delete")
    public ModelAndView deleteUser(@PathVariable String username, Model model, Principal principal) {
        Optional<User> user_opt = userService.getUserByUsername(username);
        if (user_opt.isPresent()) {
            User user = user_opt.get();
            boolean isSelf = user.getUsername().equals(principal.getName());
            if (isSelf) {
                userService.deleteUserByUsername(username);
            } else {
                model.addAttribute("notification", "No Permission to delete user");
            }
        }
        return new ModelAndView("redirect:/", model.asMap());
    }

    @RequestMapping("/{username}/edit")
    public ModelAndView editUser(@PathVariable String username, Model model, Principal principal) {
        Optional<User> user_opt = userService.getUserByUsername(username);

        if (user_opt.isPresent()) {
            User user = user_opt.get();
            boolean isSelf = user.getUsername().equals(principal.getName());

            if (isSelf) {
                model.addAttribute("user", user);
                model.addAttribute("isPerson", user.getClass().equals(Person.class));
                return new ModelAndView("user/edit", model.asMap());
            }
        }
        return new ModelAndView("redirect:/");
    }

    @PostMapping("/{username}/edit")
    public ModelAndView saveEditedUser(@Validated User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("redirect:/user/" + user.getUsername() + "/edit", "user", user);
        }
        userService.saveUser(user);
        return new ModelAndView("redirect:/user/" + user.getUsername());
    }
}
