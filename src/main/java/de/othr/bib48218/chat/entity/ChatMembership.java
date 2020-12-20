package de.othr.bib48218.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMembership extends IdEntity {
    @OneToOne
    private Chat chat;
    private ChatMemberStatus status;
    @OneToOne
    private User user;
}
