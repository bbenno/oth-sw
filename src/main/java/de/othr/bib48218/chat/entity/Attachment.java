package de.othr.bib48218.chat.entity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

@Entity
@Getter
@Setter(AccessLevel.PACKAGE)
@NoArgsConstructor(force = true)
public class Attachment extends IdEntity {

    @NonNull
    @lombok.NonNull
    @NotBlank
    private String name;

    @NonNull
    @lombok.NonNull
    @NotBlank
    private String mimeType;

    @NonNull
    @lombok.NonNull
    @NotBlank
    private String path;

    @NonNull
    @OneToOne(mappedBy = "attachment")
    private Message message;

    public Attachment(@lombok.NonNull @NonNull String name,
        @lombok.NonNull @NonNull String mimeType,
        @lombok.NonNull @NonNull String path) {
        this.name = name;
        this.mimeType = mimeType;
        this.path = path;
    }
}
