package de.othr.bib48218.chat.entity;

import lombok.Getter;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Getter
public class GroupChat extends Chat {
    private GroupVisibility visibility;
    @OneToOne
    private ChatProfile profile;

    protected GroupChat() {
        super();
    }

    public GroupChat(GroupVisibility visibility, ChatProfile profile) {
        super();
        this.visibility = visibility;
        this.profile = profile;
    }
}
