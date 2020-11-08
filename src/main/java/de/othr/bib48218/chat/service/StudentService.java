package de.othr.bib48218.chat.service;

import de.othr.bib48218.chat.entity.Student;
import de.othr.bib48218.chat.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/students")
    public Student registerStudent() {
        // TODO: use provided student instead of created dummy
        Student student = new Student(1234L, "Benno", "Bielmeier", "IN");

        student = studentRepository.save(student);

        return student;
    }
}
