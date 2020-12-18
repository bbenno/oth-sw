package de.othr.bib48218.chat.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor
public class Bot extends User {
    public Bot(String username, String password, UserProfile profile) {
        super(username, password, profile);
    }
}
