package de.othr.bib48218.chat.repository;

import de.othr.bib48218.chat.entity.Profile;
import de.othr.bib48218.chat.entity.UserProfile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface ProfileRepository<TProfile extends Profile> extends CrudRepository<TProfile, Long> {
}
