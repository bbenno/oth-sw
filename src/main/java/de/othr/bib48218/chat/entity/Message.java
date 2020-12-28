package de.othr.bib48218.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Message extends IdEntity {
    @NonNull
    @lombok.NonNull
    @NotNull
    @NotBlank
    private String text;

    @OneToOne(cascade = CascadeType.ALL)
    @NonNull
    @lombok.NonNull
    @NotNull
    private Chat chat;

    @OneToOne(cascade = CascadeType.ALL)
    @NonNull
    @lombok.NonNull
    @NotNull
    private User author;

    @NonNull
    @lombok.NonNull
    @NotNull
    private LocalDateTime timestamp;

    @OneToOne(cascade = CascadeType.ALL)
    private Attachment attachment;

    @OneToOne
    private Message replyOf;

    public Message(@NonNull String text,
                   @NonNull Chat chat,
                   @NonNull User author,
                   @NonNull LocalDateTime timestamp) {
        this.text = text;
        this.chat = chat;
        this.author = author;
        this.timestamp = timestamp;
    }

    public Message(@NonNull String text,
                   @NonNull Chat chat,
                   @NonNull User author,
                   @NonNull LocalDateTime timestamp,
                   @NonNull Attachment attachment) {
        this(text, chat, author, timestamp);
        this.attachment = attachment;
    }

    public Message(@NonNull String text,
                   @NonNull Chat chat,
                   @NonNull User author,
                   @NonNull LocalDateTime timestamp,
                   @NonNull Message replyOf) {
        this(text, chat, author, timestamp);
        this.replyOf = replyOf;
    }
}
