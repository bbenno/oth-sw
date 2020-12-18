package de.othr.bib48218.chat.controller;

import de.othr.bib48218.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public String showLoginPage(Model model) {
        return "login";
    }

    @PostMapping("/login")
    public ModelAndView loginUser(ModelMap model, @RequestParam("username") String username, @RequestParam("password") String password) {
        // TODO: Check login credentials
        model.addAttribute("login", "success");
        return new ModelAndView("redirect:/", model);
    }
}
