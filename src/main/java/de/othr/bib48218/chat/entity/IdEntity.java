package de.othr.bib48218.chat.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@EqualsAndHashCode
public abstract class IdEntity {
    @Id
    // Generating id with the GenerationType.IDENTITY has drawbacks concerning performance.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
