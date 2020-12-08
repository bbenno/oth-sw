package de.othr.bib48218.chat.repository;

import de.othr.bib48218.chat.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface UserRepository<TUser extends User> extends CrudRepository<TUser, String> {
}
