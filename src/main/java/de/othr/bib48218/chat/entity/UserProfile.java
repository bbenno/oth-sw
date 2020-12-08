package de.othr.bib48218.chat.entity;

import javax.persistence.Entity;

@Entity
public class UserProfile extends IdEntity {
    private String bio;
    private String country;

    protected UserProfile() {
    }

    public UserProfile(String bio, String country) {
        this.bio = bio;
        this.country = country;
    }

    public String getBio() {
        return this.bio;
    }

    public String getCountry() {
        return this.country;
    }
}
