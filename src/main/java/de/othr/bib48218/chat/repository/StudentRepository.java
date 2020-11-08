package de.othr.bib48218.chat.repository;

import de.othr.bib48218.chat.entity.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Long> {
}
