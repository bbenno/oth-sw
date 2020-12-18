package de.othr.bib48218.chat.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor
public class UserProfile extends IdEntity {
    private String bio;
    private String country;

    public UserProfile(String bio, String country) {
        this.bio = bio;
        this.country = country;
    }
}
