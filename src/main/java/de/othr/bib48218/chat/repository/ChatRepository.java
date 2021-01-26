package de.othr.bib48218.chat.repository;

import de.othr.bib48218.chat.entity.Chat;
import de.othr.bib48218.chat.entity.User;
import java.util.Collection;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * CRUD repository of chats.
 *
 * @param <TChat> the chat type
 */
@NoRepositoryBean
interface ChatRepository<TChat extends Chat> extends CrudRepository<TChat, Long> {

    Collection<TChat> findByMembershipsUser(User user);
}
