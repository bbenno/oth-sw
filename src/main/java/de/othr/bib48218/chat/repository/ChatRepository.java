package de.othr.bib48218.chat.repository;

import de.othr.bib48218.chat.entity.Chat;
import org.springframework.data.repository.CrudRepository;

public interface ChatRepository extends CrudRepository<Chat, Long> {
}
