package de.othr.bib48218.chat.entity;

import lombok.*;
import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
public abstract class User {
    @Id
    @NonNull
    private String username;
    @NonNull
    private String password;
    @OneToOne
    private UserProfile profile;
}
