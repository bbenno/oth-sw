package de.othr.bib48218.chat.repository;

import de.othr.bib48218.chat.entity.Chat;
import de.othr.bib48218.chat.entity.ChatMembership;
import de.othr.bib48218.chat.entity.User;
import java.util.Collection;
import org.springframework.data.repository.CrudRepository;

public interface ChatMembershipRepository extends CrudRepository<ChatMembership, Long> {

    Collection<ChatMembership> findByUser(User user);

    Collection<ChatMembership> findByChat(Chat chat);
}
