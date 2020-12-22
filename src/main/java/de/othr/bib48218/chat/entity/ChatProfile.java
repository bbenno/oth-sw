package de.othr.bib48218.chat.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ChatProfile extends Profile {
    @NonNull
    private String description;

    public ChatProfile(@NonNull String name, @NonNull String description) {
        super(name);
        this.description = description;
    }
}
