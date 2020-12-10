package de.othr.bib48218.chat.entity;

import lombok.Getter;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.ZonedDateTime;

@Entity
@Getter
public class Message extends IdEntity {
    private String text;
    @Temporal(TemporalType.TIMESTAMP)
    private ZonedDateTime timestamp;
    @OneToOne
    private Attachment attachment;
    @OneToOne
    private User author;
    @OneToOne
    private Message replyOf;

    protected Message() {}
    public Message(String text, ZonedDateTime timestamp, Attachment attachment, User author, Message replyOf) {
        this.text = text;
        this.timestamp = timestamp;
        this.attachment = attachment;
        this.author = author;
        this.replyOf = replyOf;
    }
}
