package de.othr.bib48218.chat.entity;

import lombok.Getter;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Getter
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
}
