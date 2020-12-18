package de.othr.bib48218.chat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @RequestMapping("/home")
    public String getHome(Model model) {
        return "home";
    }

    @RequestMapping("/")
    public String getIndex(Model model) {
        return "home";
    }
}
