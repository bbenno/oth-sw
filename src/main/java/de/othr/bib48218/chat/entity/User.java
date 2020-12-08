package de.othr.bib48218.chat.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;

@Entity
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

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public UserProfile getProfile() {
        return this.profile;
    }
}
