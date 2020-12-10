package de.othr.bib48218.chat.entity;

import lombok.Getter;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Profile extends IdEntity {
    private String name;
    private String imagePath;

    protected Profile() {}

    protected Profile(String name, String imagePath) {
        this.name = name;
        this.imagePath = imagePath;
    }
}
