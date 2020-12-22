package de.othr.bib48218.chat.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Attachment extends IdEntity {
    @NonNull
    private String name;
    @NonNull
    private String mimeType;
}
