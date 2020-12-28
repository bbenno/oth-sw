package de.othr.bib48218.chat.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public abstract class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    private Collection<Message> messages;

    @OneToMany(mappedBy = "chat")
    private Collection<ChatMembership> memberships;
}
