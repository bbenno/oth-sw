package de.othr.bib48218.chat.entity;

import lombok.Getter;
import javax.persistence.Entity;

@Entity
@Getter
public class UserProfile extends IdEntity {
    private String bio;
    private String country;

    protected UserProfile() {
    }

    public UserProfile(String bio, String country) {
        this.bio = bio;
        this.country = country;
    }
}
