package de.othr.bib48218.chat.entity;

import lombok.Getter;
import javax.persistence.Entity;

@Entity
@Getter
public class Attachment extends IdEntity {
    private int size;
    private String name;
    private String mimeType;

    protected Attachment() {
    }

    public Attachment(int size, String name, String mimeType) {
        this.size = size;
        this.name = name;
        this.mimeType = mimeType;
    }
}
