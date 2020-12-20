package de.othr.bib48218.chat.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor
public class Attachment extends IdEntity {
    private int size;
    private String name;
    private String mimeType;
}
