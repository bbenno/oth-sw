package de.othr.bib48218.chat.repository;

import de.othr.bib48218.chat.entity.Chat;
import de.othr.bib48218.chat.entity.Message;
import de.othr.bib48218.chat.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;

public interface MessageRepository extends PagingAndSortingRepository<Message, Long> {
    Collection<Message> findByAuthor(User author);

    Collection<Message> findByChat(Chat chat);
}
