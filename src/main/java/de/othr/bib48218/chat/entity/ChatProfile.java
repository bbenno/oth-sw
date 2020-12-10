package de.othr.bib48218.chat.entity;

import lombok.Getter;
import javax.persistence.Entity;

@Entity
@Getter
public class ChatProfile extends IdEntity {
    private String description;

    protected ChatProfile() {
    }
}
