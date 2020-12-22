package de.othr.bib48218.chat.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor
public class ChatProfile extends Profile {
    @NonNull
    private String description;

    public ChatProfile(final String name, final String description) {
        super(name);
        this.description = description;
    }
}
