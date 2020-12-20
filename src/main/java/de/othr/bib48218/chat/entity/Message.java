package de.othr.bib48218.chat.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.time.ZonedDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Message extends IdEntity {
    private String text;
    private ZonedDateTime timestamp;
    @OneToOne
    private Attachment attachment;
    @OneToOne
    private User author;
    @OneToOne
    private Message replyOf;

    public Message(String text, ZonedDateTime timestamp, Attachment attachment, User author, Message replyOf) {
        this.text = text;
        this.timestamp = timestamp;
        this.attachment = attachment;
        this.author = author;
        this.replyOf = replyOf;
    }
}
