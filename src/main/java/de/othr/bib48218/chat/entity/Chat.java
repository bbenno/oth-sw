package de.othr.bib48218.chat.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public abstract class Chat implements HeaderSearchElement {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @OneToMany(
        mappedBy = "chat",
        cascade = {CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.DETACH},
        orphanRemoval = true)
    private List<Message> messages = Collections.emptyList();

    @OneToMany(
        mappedBy = "chat",
        fetch = FetchType.EAGER,
        cascade = {CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.DETACH},
        orphanRemoval = true)
    private Set<ChatMembership> memberships = Collections.emptySet();

    @Override
    public String toString() {
        String s = getClass().getSimpleName();
        if (id != null) {
            s += id.toString();
        }
        return s;
    }
}
