package de.othr.bib48218.chat.entity;

import javax.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Entity
@Getter
@NoArgsConstructor
public class UserProfile extends Profile {

    private String bio;

    private String country;

    public UserProfile(@NonNull String name) {
        super(name);
    }
}
