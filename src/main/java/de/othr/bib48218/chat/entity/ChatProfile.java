package de.othr.bib48218.chat.entity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ChatProfile extends Profile {

    @NonNull
    @lombok.NonNull
    private String description;

    @OneToOne(mappedBy = "profile")
    private GroupChat chat;

    public ChatProfile(@lombok.NonNull @NonNull String name,
        @lombok.NonNull @NonNull String description) {
        super(name);
        this.description = description;
    }
}
