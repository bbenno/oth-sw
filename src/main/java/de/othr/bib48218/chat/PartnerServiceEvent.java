package de.othr.bib48218.chat;

import de.othr.bib48218.chat.entity.ServiceType;
import org.springframework.context.ApplicationEvent;

public class PartnerServiceEvent extends ApplicationEvent {

    private ServiceType serviceType;

    public PartnerServiceEvent(Object source, ServiceType serviceType) {
        super(source);
        this.serviceType = serviceType;
    }
}
