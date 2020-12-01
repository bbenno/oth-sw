package de.othr.bib48218.chat.entity;

public abstract class User {
    @Column(unique = true)
    private String username;
    private String password;
    private UserProfile profile;
}
