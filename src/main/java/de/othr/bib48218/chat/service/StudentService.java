package de.othr.bib48218.chat.service;

import de.othr.bib48218.chat.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    // TODO
    @GetMapping("/hello")
    public String helloWorld(@RequestParam(value = "name", defaultValue = "world") String input) {
        return "Hello, " + input + "!";
    }
}
