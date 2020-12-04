package de.othr.bib48218.chat.repository;

import de.othr.bib48218.chat.entity.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long> {
}
