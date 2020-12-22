package de.othr.bib48218.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMembership extends IdEntity {
    @OneToOne
    @NonNull
    private Chat chat;
    @NonNull
    private ChatMemberStatus status;
    @OneToOne
    @NonNull
    private User user;
}
