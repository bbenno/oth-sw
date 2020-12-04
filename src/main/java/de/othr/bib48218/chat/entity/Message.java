package de.othr.bib48218.chat.entity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.time.ZonedDateTime;

@Entity
public class Message extends IdEntity {
    private String text;
    private ZonedDateTime timestamp;
    @OneToOne
    private Attachment attachment;
    @OneToOne
    private User author;
    @OneToOne
    private Message replyOf;
}
