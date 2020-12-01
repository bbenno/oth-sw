package de.othr.bib48218.chat.entity;

import org.springframework.lang.Nullable;

import java.time.ZonedDateTime;

public class Message {
    private String text;
    private ZonedDateTime timestamp;
    private Attachment attachment;
    @Nullable
    private User author;
    @Nullable
    private Message replyOf;
}
