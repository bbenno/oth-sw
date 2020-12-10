package de.othr.bib48218.chat.entity;

import lombok.Getter;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User {
    @Id
    private String username;
    private String password;
    @OneToOne
    private UserProfile profile;

    protected User() {
    }

    protected User(String username, String password, UserProfile profile) {
        this.username = username;
        this.password = password;
        this.profile = profile;
    }
}
