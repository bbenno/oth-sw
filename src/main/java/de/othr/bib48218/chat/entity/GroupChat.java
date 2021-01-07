package de.othr.bib48218.chat.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class GroupChat extends Chat {
    @NonNull
    @lombok.NonNull
    private GroupVisibility visibility;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private ChatProfile profile;

    @Override
    public String toString() {
        if (profile == null) {
            String s = super.toString();
            if (visibility != null)
                s += " (" + visibility + ")";
            return s;
        } else {
            return profile.getName();
        }
    }
}
