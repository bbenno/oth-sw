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
public abstract class Chat implements HeaderSearchElement {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    private Collection<Message> messages;

    @OneToMany(mappedBy = "chat")
    private Collection<ChatMembership> memberships;

    @Override
    public String toString() {
        String s = getClass().getSimpleName();
        if (id != null) {
            s += id.toString();
        }
        return s;
    }
}
