package de.othr.bib48218.chat.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

/**
 * The text sent by an {@link User} in a certain {@link Chat}.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message extends IdEntity {

    /**
     * The message text.
     */
    @NonNull
    @lombok.NonNull
    @NotNull
    @NotBlank
    private String text;

    /**
     * The containing chat.
     */
    @ManyToOne(
        cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE},
        optional = false)
    @NonNull
    @lombok.NonNull
    @NotNull
    private Chat chat;

    /**
     * The sending user.
     */
    @ManyToOne(
        fetch = FetchType.EAGER,
        cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE},
        optional = false)
    @NonNull
    @lombok.NonNull
    @NotNull
    private User author;

    /**
     * The point in time when message is sent.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @NonNull
    @lombok.NonNull
    @NotNull
    @PastOrPresent
    private LocalDateTime timestamp;

    /**
     * The attachment.
     */
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Attachment attachment;

    /**
     * The message replied to.
     */
    @OneToOne(cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE})
    private Message replyOf;

    /**
     * Class constructor specifying basic attributes.
     *
     * @param text      the message text
     * @param chat      the containing chat
     * @param author    the user sending
     * @param timestamp the point in time when message is sent
     */
    public Message(@lombok.NonNull @NonNull String text,
        @lombok.NonNull @NonNull Chat chat,
        @lombok.NonNull @NonNull User author,
        @lombok.NonNull @NonNull LocalDateTime timestamp) {
        this.text = text;
        this.chat = chat;
        this.author = author;
        this.timestamp = timestamp;
    }

    /**
     * Class constructor specifying basic attributes and attachment.
     *
     * @param text       the message text
     * @param chat       the containing chat
     * @param author     the user sending
     * @param timestamp  the point in time when message is sent
     * @param attachment the attachment
     */
    public Message(@lombok.NonNull @NonNull String text,
        @lombok.NonNull @NonNull Chat chat,
        @lombok.NonNull @NonNull User author,
        @lombok.NonNull @NonNull LocalDateTime timestamp,
        @lombok.NonNull @NonNull Attachment attachment) {
        this(text, chat, author, timestamp);
        this.attachment = attachment;
    }

    /**
     * Class constructor specifying basic attributes and attachment.
     *
     * @param text      the message text
     * @param chat      the containing chat
     * @param author    the user sending
     * @param timestamp the point in time when message is sent
     * @param replyOf   the message replied to
     */
    public Message(@lombok.NonNull @NonNull String text,
        @lombok.NonNull @NonNull Chat chat,
        @lombok.NonNull @NonNull User author,
        @lombok.NonNull @NonNull LocalDateTime timestamp,
        @lombok.NonNull @NonNull Message replyOf) {
        this(text, chat, author, timestamp);
        this.replyOf = replyOf;
    }
}
