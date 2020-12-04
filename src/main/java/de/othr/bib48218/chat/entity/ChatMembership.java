package de.othr.bib48218.chat.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class ChatMembership {
    @Id
    private Long id;
    private ChatMemberStatus status;
    @OneToOne
    private Chat chat;
    @OneToOne
    private User user;
}
