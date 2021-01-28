package de.othr.bib48218.chat.entity;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class Attachment {

    /**
     * The file name.
     */
    @NonNull
    @lombok.NonNull
    @NotBlank
    private String name;

    /**
     * The file type.
     */
    @NonNull
    @lombok.NonNull
    @NotBlank
    private String mimeType;

    /**
     * The file path.
     */
    @NonNull
    @lombok.NonNull
    @NotBlank
    private String path;

    /**
     * Class constructor specifying name, path and MIME type of the attachment file.
     *
     * @param name     the file name
     * @param mimeType the file type
     * @param path     the file path
     */
    public Attachment(@lombok.NonNull @NonNull String name,
        @lombok.NonNull @NonNull String mimeType,
        @lombok.NonNull @NonNull String path) {
        this.name = name;
        this.mimeType = mimeType;
        this.path = path;
    }
}
