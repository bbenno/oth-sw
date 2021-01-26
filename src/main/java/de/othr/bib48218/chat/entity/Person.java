package de.othr.bib48218.chat.entity;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Person extends User {

    @Size(max = 100)
    @Nullable
    private String firstName;

    @Size(max = 100)
    @Nullable
    private String lastName;

    @Email(message = "Email should be valid")
    @Size(max = 100)
    @Nullable
    private String email;

    public Person(@NonNull String username, @NonNull String password) {
        super(username, password);
    }

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
