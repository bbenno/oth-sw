package de.othr.bib48218.chat.entity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class GroupChat extends Chat {
    private GroupVisibility visibility;
    @OneToOne
    private ChatProfile profile;
}
