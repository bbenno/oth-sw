package de.othr.bib48218.chat;

import de.othr.bib48218.chat.entity.Message;
import de.othr.bib48218.chat.entity.ServiceType;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PartnerServiceEvent extends ApplicationEvent {

    private final ServiceType serviceType;

    public PartnerServiceEvent(Message source, ServiceType serviceType) {
        super(source);
        this.serviceType = serviceType;
    }

    @Override
    public Message getSource() {
        return (Message) this.source;
    }

}
