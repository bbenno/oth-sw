package de.othr.bib48218.chat.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Collection;

@Entity
@Getter
@NoArgsConstructor
public class Person extends User {
    private String firstName;
    private String lastName;
    private String email;
    @OneToMany
    private Collection<ServiceCredential> credentials;

    public Person(String username, String password, UserProfile profile, String firstName, String lastName, String email) {
        super(username, password, profile);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
