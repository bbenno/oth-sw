package de.othr.bib48218.chat.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
import lombok.Setter;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    property = "type")
@JsonSubTypes({
    @Type(value = PeerChat.class, name = "peer_chat"),
    @Type(value = GroupChat.class, name = "group_chat")
})
@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public abstract class Chat {

    /**
     * The identification number.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    /**
     * The sent messages in the chat.
     */
    @JsonIgnore
    @OneToMany(
        mappedBy = "chat",
        cascade = {CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.DETACH},
        orphanRemoval = true)
    private List<Message> messages = Collections.emptyList();

    /**
     * The memberships of {@link User}.
     */
    @OneToMany(
        mappedBy = "chat",
        fetch = FetchType.EAGER,
        cascade = CascadeType.ALL,
        orphanRemoval = true)
    private Set<ChatMembership> memberships = Collections.emptySet();

    /**
     * Gets membership status of a certain user.
     * @param user the user
     * @return the membership status
     */
    public Optional<ChatMemberStatus> getStatusOfMember(User user) {
        return memberships.stream()
            .filter(m -> m.getUser() == user)
            .map(ChatMembership::getStatus)
            .findAny();
    }

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
