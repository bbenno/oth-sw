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
}
