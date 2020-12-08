package de.othr.bib48218.chat.entity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Collection;

@Entity
public abstract class Chat extends IdEntity {
    @OneToMany
    private Collection<Message> messages;

    public Collection<Message> getMessages() {
        return this.messages;
    }

    protected Chat() {
    }
}
