package de.othr.bib48218.chat.repository;

import de.othr.bib48218.chat.entity.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ProfileRepository<TProfile extends Profile> extends CrudRepository<TProfile, Long> {
}
