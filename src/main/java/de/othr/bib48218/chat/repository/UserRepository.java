package de.othr.bib48218.chat.repository;

import de.othr.bib48218.chat.entity.Chat;
import de.othr.bib48218.chat.entity.ChatMemberStatus;
import de.othr.bib48218.chat.entity.Permission;
import de.othr.bib48218.chat.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Collection;
import java.util.Optional;

@NoRepositoryBean
public interface UserRepository<TUser extends User> extends CrudRepository<TUser, String> {
    Optional<TUser> findByUsername(String username);

    Collection<TUser> findByUsernameLike(String usernamePattern);

    Collection<TUser> findByProfileName(String name);

    Collection<TUser> findByProfileCountry(String country);

    Collection<TUser> findByUserPermissionsPermission(Permission permission);

    Collection<TUser> findByMembershipsChat(Chat chat);

    Collection<TUser> findByMembershipsChatAndMembershipsStatus(Chat chat, ChatMemberStatus status);
}
