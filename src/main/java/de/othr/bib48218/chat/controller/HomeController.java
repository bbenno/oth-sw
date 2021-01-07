package de.othr.bib48218.chat.controller;

import de.othr.bib48218.chat.service.IFChatService;
import de.othr.bib48218.chat.service.IFUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@SuppressWarnings("SameReturnValue")
@Controller
public class HomeController {
    @Autowired
    IFChatService chatService;

    @Autowired
    IFUserService userService;

    @RequestMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @RequestMapping("/logout")
    public String showLogoutPage() {
        return "logout";
    }

    @RequestMapping("/")
    public String showHome(Principal principal, Model model) {
        var user = userService.getUserByUsername(principal.getName());
        var chats = chatService.getChatsByUser(user);
        model.addAttribute("chats", chats);
        return "home";
    }

    @RequestMapping("/home")
    public ModelAndView showHomeAlias(Model model) {
        return new ModelAndView("redirect:/", model.asMap());
    }

    @RequestMapping("/me")
    public ModelAndView showMe(Principal principal) {
        return new ModelAndView("redirect:/user/" + principal.getName());
    }
}
