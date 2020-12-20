package de.othr.bib48218.chat.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Getter
@NoArgsConstructor
public class GroupChat extends Chat {
    private GroupVisibility visibility;
    @OneToOne
    private ChatProfile profile;

    public GroupChat(GroupVisibility visibility, ChatProfile profile) {
        super();
        this.visibility = visibility;
        this.profile = profile;
    }
}
