package de.othr.bib48218.chat.repository;

import de.othr.bib48218.chat.entity.Chat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ChatRepository<TChat extends Chat> extends CrudRepository<TChat, Long> {
}
