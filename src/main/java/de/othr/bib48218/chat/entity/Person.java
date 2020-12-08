package de.othr.bib48218.chat.entity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Collection;

@Entity
public class Person extends User {
    private String firstName;
    private String lastName;
    private String email;
    @OneToMany
    private Collection<ServiceCredential> credentials;

    protected Person() {
    }

    public Person(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public Collection<ServiceCredential> getCredentials() {
        return this.credentials;
    }
}
