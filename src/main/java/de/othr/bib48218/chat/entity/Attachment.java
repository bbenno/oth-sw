package de.othr.bib48218.chat.entity;

import javax.persistence.Entity;

@Entity
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

    public int getSize() {
        return this.size;
    }

    public String getName() {
        return this.name;
    }

    public String getMimeType() {
        return this.mimeType;
    }
}
