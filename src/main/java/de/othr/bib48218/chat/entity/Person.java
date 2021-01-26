package de.othr.bib48218.chat.entity;

import java.util.Optional;
import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * An account of an natural person.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Person extends User {

    /**
     * The first name.
     */
    @Size(max = 100)
    @Nullable
    private String firstName;

    /**
     * The last name.
     */
    @Size(max = 100)
    @Nullable
    private String lastName;

    /**
     * The email address.
     */
    @Email(message = "Email should be valid")
    @Size(max = 100)
    @Nullable
    private String email;

    /**
     * Class constructor specifying the username and password.
     *
     * @param username the account specifier
     * @param password the account credential
     */
    public Person(@NonNull String username, @NonNull String password) {
        super(username, password);
    }

    /**
     * Class constructor specifying the username, password, and first name, last name, and email
     * address.
     *
     * @param username  the account specifier
     * @param password  the account credential
     * @param firstName the first name
     * @param lastName  the last name
     * @param email     the email address
     */
    public Person(@NonNull String username, @NonNull String password, String firstName,
        String lastName, String email) {
        super(username, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @Override
    public String asString() {
        return fullName().orElse("");
    }

    /**
     * Gets the full name consisting of first and last name.
     *
     * @return the full name
     */
    public Optional<String> fullName() {
        String fullName;
        if (getFirstName() == null) {
            fullName = getLastName();
        } else if (getLastName() == null) {
            fullName = getFirstName();
        } else {
            fullName = String.join(" ", firstName, lastName);
        }
        return Optional.ofNullable(fullName);
    }
}
