package de.othr.bib48218.chat.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message extends IdEntity {
    @NonNull
    @lombok.NonNull
    @NotNull
    @NotBlank
    private String text;

    @JsonIgnore
    @ManyToOne(
        cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE},
        optional = false)
    @NonNull
    @lombok.NonNull
    @NotNull
    private Chat chat;

    @ManyToOne(
        fetch = FetchType.EAGER,
        cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE},
        optional = false)
    @NonNull
    @lombok.NonNull
    @NotNull
    private User author;

    @NonNull
    @lombok.NonNull
    @NotNull
    @PastOrPresent
    private LocalDateTime timestamp;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Attachment attachment;

    @OneToOne(cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE})
    private Message replyOf;

    public Message(@lombok.NonNull @NonNull String text,
                   @lombok.NonNull @NonNull Chat chat,
                   @lombok.NonNull @NonNull User author,
                   @lombok.NonNull @NonNull LocalDateTime timestamp) {
        this.text = text;
        this.chat = chat;
        this.author = author;
        this.timestamp = timestamp;
    }

    public Message(@lombok.NonNull @NonNull String text,
                   @lombok.NonNull @NonNull Chat chat,
                   @lombok.NonNull @NonNull User author,
                   @lombok.NonNull @NonNull LocalDateTime timestamp,
                   @lombok.NonNull @NonNull Attachment attachment) {
        this(text, chat, author, timestamp);
        this.attachment = attachment;
    }

    public Message(@lombok.NonNull @NonNull String text,
                   @lombok.NonNull @NonNull Chat chat,
                   @lombok.NonNull @NonNull User author,
                   @lombok.NonNull @NonNull LocalDateTime timestamp,
                   @lombok.NonNull @NonNull Message replyOf) {
        this(text, chat, author, timestamp);
        this.replyOf = replyOf;
    }
}
