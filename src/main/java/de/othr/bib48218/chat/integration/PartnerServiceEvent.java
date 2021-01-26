package de.othr.bib48218.chat.integration;

import de.othr.bib48218.chat.entity.Message;
import de.othr.bib48218.chat.entity.ServiceType;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * A event triggered when a user writes a service bot that is backed by external partner service.
 */
@Getter
public class PartnerServiceEvent extends ApplicationEvent {

    private final ServiceType serviceType;

    /**
     * Class constructor.
     *
     * @param source      the message of a user to a service bot as source of the event
     * @param serviceType the service type of the service bot and partner service
     */
    public PartnerServiceEvent(Message source, ServiceType serviceType) {
        super(source);
        this.serviceType = serviceType;
    }

    /**
     * The message on which the Event initially occurred.
     *
     * @return the message on which the Event initially occurred
     */
    @Override
    public Message getSource() {
        return (Message) this.source;
    }

}
