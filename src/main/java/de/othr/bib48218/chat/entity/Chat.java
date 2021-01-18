package de.othr.bib48218.chat.entity;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private final List<Message> messages = Collections.emptyList();

    @OneToMany(
        mappedBy = "chat",
        fetch = FetchType.EAGER,
        cascade = {CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.DETACH},
        orphanRemoval = true)
    private final Set<ChatMembership> memberships = Collections.emptySet();

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        if (!(o instanceof Chat)) {
            return false;
        }
        return equals((Chat) o);
    }

    public boolean equals(Chat other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        return id.equals(other.id);
    }

    @Override
    public String toString() {
        String s = getClass().getSimpleName();
        if (id != null) {
            s += id.toString();
        }
        return s;
    }
}
