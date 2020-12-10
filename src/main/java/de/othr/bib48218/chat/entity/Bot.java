package de.othr.bib48218.chat.entity;

import lombok.Getter;
import javax.persistence.Entity;

@Entity
@Getter
public class Bot extends User {
    protected Bot() {
        super();
    }

    public Bot(String username, String password, UserProfile profile) {
        super(username, password, profile);
    }
}
