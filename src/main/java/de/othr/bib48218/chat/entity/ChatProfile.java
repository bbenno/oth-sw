package de.othr.bib48218.chat.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ChatProfile extends Profile {
    @NonNull
    @lombok.NonNull
    @NotBlank
    private String description;

    @OneToOne(mappedBy = "profile")
    private GroupChat chat;

    public ChatProfile(@lombok.NonNull @NonNull String name, @lombok.NonNull @NonNull String description) {
        super(name);
        this.description = description;
    }
}
