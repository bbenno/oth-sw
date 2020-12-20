package de.othr.bib48218.chat.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor
@RequiredArgsConstructor
public abstract class User {
    @Id
    @lombok.NonNull
    private String username;
    @lombok.NonNull
    @org.springframework.lang.NonNull
    private String password;
    @OneToOne
    private UserProfile profile;
}
