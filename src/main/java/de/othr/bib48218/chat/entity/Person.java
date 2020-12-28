package de.othr.bib48218.chat.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Person extends User {

    @Size(max = 100)
    private String firstName;

    @Size(max = 100)
    private String lastName;

    @Email(message = "Email should be valid")
    @Size(max = 100)
    private String email;

    @OneToMany(cascade = CascadeType.ALL)
    private Collection<ServiceCredential> credentials;

    public Person(@NonNull String username, @NonNull String password) {
        super(username, password);
    }

    public Person(@NonNull String username, @NonNull String password, String firstName, String lastName, String email) {
        super(username, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
