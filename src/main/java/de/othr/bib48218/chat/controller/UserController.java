package de.othr.bib48218.chat.controller;

import de.othr.bib48218.chat.entity.Bot;
import de.othr.bib48218.chat.entity.Person;
import de.othr.bib48218.chat.entity.User;
import de.othr.bib48218.chat.service.IFUserService;
import java.security.Principal;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IFUserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @RequestMapping("{username}")
    public String showUser(@PathVariable String username, Model model) {
        Optional<User> found = userService.getUserByUsername(username);

        if (found.isEmpty()) {
            return "redirect:/";
        }

        model.addAttribute("user", found.get());
        model.addAttribute("isPerson", found.get().getClass().equals(Person.class));
        return "user/show";
    }

    @RequestMapping("/{username}/delete")
    @Transactional
    public String deleteUser(
        @PathVariable String username,
        Model model,
        Principal principal
    ) {
        Optional<User> user_opt = userService.getUserByUsername(username);

        if (user_opt.isEmpty()) {
            model.addAttribute("notification", "User not found");
            return "redirect:/";
        }

        boolean isSelf = user_opt.get().getUsername().equals(principal.getName());
        if (isSelf) {
            userService.deleteUserByUsername(username);
            return "redirect:/logout";
        } else {
            model.addAttribute("notification", "No Permission to delete user");
            return "redirect:/";
        }
    }

    @RequestMapping("/{username}/edit")
    public String editUser(@PathVariable String username, Model model, Principal principal) {
        Optional<User> user_opt = userService.getUserByUsername(username);

        if (user_opt.isEmpty()) {
            return "redirect:/";
        }

        boolean isSelf = user_opt.get().getUsername().equals(principal.getName());

        if (isSelf) {
            model.addAttribute("user", user_opt.get());
            return "user/edit";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/{username}/edit-bot")
    @Transactional
    public String saveEditedBot(@Validated Bot bot, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", bot);
            return "redirect:edit";
        }

        userService.getBotByUsername(bot.getUsername()).ifPresent(b -> {
            b.setPassword(passwordEncoder.encode(bot.getPassword()));
        });
        return "redirect:";
    }

    @PostMapping("/{username}/edit-person")
    @Transactional
    public String saveEditedPerson(@Validated Person person, BindingResult bindingResult,
        Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", person);
            return "redirect:edit";
        }

        userService.getPersonByUsername(person.getUsername()).ifPresent(p -> {
            p.setEmail(person.getEmail());
            p.setFirstName(person.getFirstName());
            p.setLastName(person.getLastName());
            p.setPassword(passwordEncoder.encode(person.getPassword()));
        });
        return "redirect:";
    }
}
