package de.othr.bib48218.chat.repository;

import de.othr.bib48218.chat.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
