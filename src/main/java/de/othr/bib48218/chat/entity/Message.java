package de.othr.bib48218.chat.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Message extends IdEntity {
    @NonNull
    private String text;
    @NonNull
    private LocalDateTime timestamp;
    @OneToOne
    private Attachment attachment;
    @OneToOne
    @NonNull
    private User author;
    @OneToOne
    private Message replyOf;

    public Message(@NonNull String text,
                   @NonNull User author,
                   @NonNull LocalDateTime timestamp) {
        this.text = text;
        this.author = author;
        this.timestamp = timestamp;
    }

    public Message(@NonNull String text,
                   @NonNull User author,
                   @NonNull LocalDateTime timestamp,
                   @NonNull Attachment attachment) {
        this(text, author, timestamp);
        this.attachment = attachment;
    }

    public Message(@NonNull String text,
                   @NonNull User author,
                   @NonNull LocalDateTime timestamp,
                   @NonNull Attachment attachment,
                   @NonNull Message replyOf) {
        this(text, author, timestamp, attachment);
        this.replyOf = replyOf;
    }

    public Message(@NonNull String text,
                   @NonNull User author,
                   @NonNull LocalDateTime timestamp,
                   @NonNull Message replyOf) {
        this(text, author, timestamp);
        this.replyOf = replyOf;
    }
}
