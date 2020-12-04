package de.othr.bib48218.chat.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class IdEntity {
    @Id
    // Generating id with the GenerationType.IDENTITY has drawbacks concerning performance.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if(!o.getClass().equals(this.getClass()))
            return false;
        IdEntity other = (IdEntity) o;
        return this.id == other.id;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(this.id);
    }
}
