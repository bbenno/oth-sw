package de.othr.bib48218.chat.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Collection;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Person extends User {
    private String firstName;
    private String lastName;
    private String email;
    @OneToMany
    private Collection<ServiceCredential> credentials;

    public Person(@NonNull String username, @NonNull String password) {
        super(username, password);
    }

    public Person(@NonNull String username,@NonNull  String password, String firstName, String lastName, String email) {
        super(username, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
