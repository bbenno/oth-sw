package de.othr.bib48218.chat.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Getter
@NoArgsConstructor
public class UserProfile extends Profile {
    private String bio;

    private String country;

    @OneToOne(mappedBy = "profile")
    private User user;

    public UserProfile(@NonNull String name) {
        super(name);
    }
}
