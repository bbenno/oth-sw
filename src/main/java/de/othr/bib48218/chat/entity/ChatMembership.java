package de.othr.bib48218.chat.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Getter
@NoArgsConstructor
public class ChatMembership extends IdEntity {
    @OneToOne
    private Chat chat;
    private ChatMemberStatus status;
    @OneToOne
    private User user;

    public ChatMembership(User user, Chat chat, ChatMemberStatus status) {
        this.status = status;
        this.chat = chat;
        this.user = user;
    }
}
