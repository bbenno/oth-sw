package de.othr.bib48218.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMembership extends IdEntity {
    @ManyToOne(cascade = CascadeType.REFRESH)
    @NonNull
    @lombok.NonNull
    private Chat chat;

    @NonNull
    @lombok.NonNull
    private ChatMemberStatus status;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @NonNull
    @lombok.NonNull
    private User user;
}
