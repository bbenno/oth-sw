package de.othr.bib48218.chat.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentService {

    // TODO
    @GetMapping("/hello")
    public String helloWorld(@RequestParam(value = "name", defaultValue = "world") String input) {
        return "Hello, " + input + "!";
    }
}
