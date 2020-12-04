package de.othr.bib48218.chat.entity;

import javax.persistence.Entity;

@Entity
public class Attachment extends IdEntity {
    private int size;
    private String name;
    private String mimeType;
}
