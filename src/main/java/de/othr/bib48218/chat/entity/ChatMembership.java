package de.othr.bib48218.chat.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

/**
 * A membership of a {@link User} in a {@link Chat}.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ChatMembership extends IdEntity {

    /**
     * The chat.
     */
    @JsonIgnore
    @ManyToOne(
        cascade = CascadeType.REFRESH,
        optional = false)
    @NonNull
    @lombok.NonNull
    private Chat chat;

    /**
     * The membership status of the user.
     */
    @NonNull
    @lombok.NonNull
    private ChatMemberStatus status;

    /**
     * The user.
     */
    @ManyToOne(
        cascade = CascadeType.REFRESH,
        optional = false)
    @NonNull
    @lombok.NonNull
    private User user;
}
