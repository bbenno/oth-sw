package de.othr.bib48218.chat.entity;

import javax.persistence.Entity;

@Entity
public class UserProfile extends IdEntity {
    private String bio;
    private String country;
}
