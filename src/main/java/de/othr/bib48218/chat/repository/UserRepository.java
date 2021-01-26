package de.othr.bib48218.chat.repository;

import de.othr.bib48218.chat.entity.Chat;
import de.othr.bib48218.chat.entity.ChatMemberStatus;
import de.othr.bib48218.chat.entity.Permission;
import de.othr.bib48218.chat.entity.User;
import java.util.Collection;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * CRUD repository of users.
 *
 * @param <TUser> the user type
 */
@NoRepositoryBean
interface UserRepository<TUser extends User> extends CrudRepository<TUser, String> {

    Optional<TUser> findByUsername(String username);

    Collection<TUser> findByUsernameLike(String usernamePattern);

    Collection<TUser> findByUsernameContains(String usernameFragment);

    Collection<TUser> findByProfileName(String name);

    Collection<TUser> findByProfileNameContains(String profileNameFragment);

    Collection<TUser> findByProfileCountry(String country);

    Collection<TUser> findByUserPermissionsPermission(Permission permission);

    Collection<TUser> findByMembershipsChat(Chat chat);

    Collection<TUser> findByMembershipsChatAndMembershipsStatus(Chat chat, ChatMemberStatus status);
}
