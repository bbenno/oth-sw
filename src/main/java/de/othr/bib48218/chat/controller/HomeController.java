package de.othr.bib48218.chat.controller;

import de.othr.bib48218.chat.entity.Chat;
import de.othr.bib48218.chat.entity.Person;
import de.othr.bib48218.chat.entity.User;
import de.othr.bib48218.chat.service.IFChatService;
import de.othr.bib48218.chat.service.IFUserService;
import de.othr.bib48218.chat.util.UserAlreadyExistsException;
import java.security.Principal;
import java.util.Collection;
import javax.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The controller for common view: login, logout, home.
 */
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Controller
public class HomeController {

    @Autowired
    IFChatService chatService;

    @Autowired
    IFUserService userService;

    @RequestMapping("/register")
    public String showPersonForm(Model model) {
        model.addAttribute("user", new Person());
        return "register";
    }

    @PostMapping("/register")
    public String createPerson(
        @Validated Person person,
        BindingResult bindingResult,
        Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", person);
            return "register";
        }

        try {
            person = userService.createPerson(person);
            return "redirect:/user/" + person.getUsername();
        } catch (UserAlreadyExistsException userAlreadyExistsException) {
            bindingResult.addError(new FieldError(
                bindingResult.getObjectName(),
                "username",
                "Username exists already"
            ));

            person.setUsername("");
            model.addAttribute("user", person);
            return "register";
        }
    }

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

    @InitBinder
    void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    private User userOfPrincipal(Principal principal) {
        return userService.getUserByUsername(principal.getName()).orElseThrow();
    }
}
