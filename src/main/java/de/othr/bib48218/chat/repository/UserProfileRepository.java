package de.othr.bib48218.chat.repository;

import de.othr.bib48218.chat.entity.User;
import de.othr.bib48218.chat.entity.UserProfile;

import java.util.Optional;

public interface UserProfileRepository extends ProfileRepository<UserProfile> {
    Optional<UserProfile> findByUser(User user);
}
