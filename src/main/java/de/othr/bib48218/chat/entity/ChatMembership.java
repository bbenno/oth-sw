package de.othr.bib48218.chat.entity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class ChatMembership extends IdEntity {
    @OneToOne
    private Chat chat;
    private ChatMemberStatus status;
    @OneToOne
    private User user;

    protected ChatMembership() {
    }

    public ChatMembership(User user, Chat chat, ChatMemberStatus status) {
        this.status = status;
        this.chat = chat;
        this.user = user;
    }

    public Chat getChat() {
        return this.chat;
    }

    public ChatMemberStatus getStatus() {
        return this.status;
    }

    public User getUser() {
        return this.user;
    }
}
