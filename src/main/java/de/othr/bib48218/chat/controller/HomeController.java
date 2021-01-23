package de.othr.bib48218.chat.controller;

import de.othr.bib48218.chat.entity.Chat;
import de.othr.bib48218.chat.entity.User;
import de.othr.bib48218.chat.service.IFChatService;
import de.othr.bib48218.chat.service.IFUserService;
import java.security.Principal;
import java.util.Collection;
import javax.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String showHome(
        @PathParam("notification") String notification,
        Principal principal,
        Model model
    ) {
        Collection<Chat> chats = chatService.getChatsByUser(userOfPrincipal(principal));

        model.addAttribute("notification", notification);
        model.addAttribute("chats", chats);
        return "home";
    }

    @RequestMapping("/home")
    public String showHomeAlias() {
        return "redirect:/";
    }

    @RequestMapping("/me")
    public String showMe(Principal principal) {
        return "redirect:/user/" + principal.getName();
    }

    private User userOfPrincipal(Principal principal) {
        return userService.getUserByUsername(principal.getName()).orElseThrow();
    }
}
