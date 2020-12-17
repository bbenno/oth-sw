package de.othr.bib48218.chat.controller;

import de.othr.bib48218.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public String showLoginPage(Model model) {
        return "login";
    }

    @PutMapping("/login")
    public String loginUser(Model model) {
        return "home";
    }
}
