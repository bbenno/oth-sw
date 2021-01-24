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

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ChatMembership extends IdEntity {

    @JsonIgnore
    @ManyToOne(
        cascade = CascadeType.REFRESH,
        optional = false)
    @NonNull
    @lombok.NonNull
    private Chat chat;

    @NonNull
    @lombok.NonNull
    private ChatMemberStatus status;

    @ManyToOne(
        cascade = CascadeType.REFRESH,
        optional = false)
    @NonNull
    @lombok.NonNull
    private User user;
}
