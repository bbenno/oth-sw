package de.othr.bib48218.chat.entity;

import javax.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Bot extends User {

    public Bot(@lombok.NonNull @NonNull String username, @lombok.NonNull @NonNull String password) {
        super(username, password);
    }

    @Override
    public String toString() {
        return super.toString() + " (Bot)";
    }
}
