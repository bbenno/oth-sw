package de.othr.bib48218.chat.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

/**
 * A public profile.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Profile {

    /**
     * A unique identifier.
     */
    @JsonIgnore
    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    /**
     * The profile name.
     */
    @NonNull
    private String name;

    /**
     * The path to profile image.
     */
    private String imagePath;

    /**
     * Class constructor specifying the profile name.
     *
     * @param name the string of profile name
     */
    protected Profile(String name) {
        this.name = name;
    }
}
