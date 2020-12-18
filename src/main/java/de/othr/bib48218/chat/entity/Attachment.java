package de.othr.bib48218.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Attachment extends IdEntity {
    private int size;
    @NonNull
    private String name;
    @NonNull
    private String mimeType;

    public Attachment(int size, String name, String mimeType) {
        this.size = size;
        this.name = name;
        this.mimeType = mimeType;
    }
}
