package de.othr.bib48218.chat.entity;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User extends IdEntity {
    @Column(unique = true)
    private String username;
    private String password;
    @OneToOne
    private UserProfile profile;
}
