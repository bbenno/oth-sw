package de.othr.bib48218.chat.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * A super class for other entity classes.
 */
@MappedSuperclass
@Getter
@EqualsAndHashCode
abstract class IdEntity {

    /**
     * A identification number.
     */
    @Id
    // Generating id with the GenerationType.IDENTITY has drawbacks concerning performance.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
