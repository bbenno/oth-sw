package de.othr.bib48218.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PACKAGE;

@Entity
@Getter
@Setter(PACKAGE)
@NoArgsConstructor
@AllArgsConstructor
public class Message extends IdEntity {
    @NonNull
    @lombok.NonNull
    @NotNull
    @NotBlank
    private String text;

    @ManyToOne(cascade = CascadeType.ALL)
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
    @PastOrPresent
    private LocalDateTime timestamp;

    @OneToOne(cascade = CascadeType.ALL)
    private Attachment attachment;

    @OneToOne(cascade = CascadeType.ALL)
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
