package de.othr.bib48218.chat.repository;

import de.othr.bib48218.chat.entity.Chat;
import de.othr.bib48218.chat.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Collection;

@NoRepositoryBean
public interface ChatRepository<TChat extends Chat> extends CrudRepository<TChat, Long> {
    Collection<TChat> findByMembershipsUser(User user);
}
