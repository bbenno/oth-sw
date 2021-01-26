package de.othr.bib48218.chat.entity;

import javax.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

/**
 * The public profile of an {@link User}.
 */
@Entity
@Getter
@NoArgsConstructor
public class UserProfile extends Profile {

    /**
     * Bibliography of the user.
     */
    private String bio;

    /**
     * Country of the user.
     */
    private String country;

    public UserProfile(@NonNull String name) {
        super(name);
    }
}
