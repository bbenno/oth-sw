package de.othr.bib48218.chat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {
    @RequestMapping
    public String showLoginPage(Model model) {
        return "login";
    }
}
