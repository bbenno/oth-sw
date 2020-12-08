package de.othr.bib48218.chat.entity;

import javax.persistence.Entity;

@Entity
public class Bot extends User {
    protected Bot() {
        super();
    }

    public Bot(String username, String password, UserProfile profile) {
        super(username, password, profile);
    }
}
