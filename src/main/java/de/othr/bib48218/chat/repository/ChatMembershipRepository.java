package de.othr.bib48218.chat.repository;

import de.othr.bib48218.chat.entity.Chat;
import de.othr.bib48218.chat.entity.ChatMembership;
import de.othr.bib48218.chat.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface ChatMembershipRepository extends CrudRepository<ChatMembership, Long> {
    Collection<ChatMembership> findByUser(User user);

    Collection<ChatMembership> findByChat(Chat chat);
}
