package de.othr.bib48218.chat.entity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
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

    public GroupVisibility getVisibility() {
        return this.visibility;
    }

    public ChatProfile getProfile() {
        return this.profile;
    }
}
