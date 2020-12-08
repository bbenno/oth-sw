package de.othr.bib48218.chat.entity;

import javax.persistence.Entity;

@Entity
public class ChatProfile extends IdEntity {
    private String description;

    protected ChatProfile() {
    }

    public ChatProfile(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
