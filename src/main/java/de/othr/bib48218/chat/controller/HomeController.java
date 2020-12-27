package de.othr.bib48218.chat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    @RequestMapping("/login")
    public String showLoginPage(Model model) {
        return "login";
    }

    @RequestMapping("/")
    public String showHome(Model model) {
        return "home";
    }

    @RequestMapping("/home")
    public ModelAndView showHomeAlias(Model model) {
        return new ModelAndView("redirect:/", model.asMap());
    }
}
