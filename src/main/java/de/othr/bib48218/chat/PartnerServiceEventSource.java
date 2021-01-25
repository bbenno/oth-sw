package de.othr.bib48218.chat;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

@Component
public class PartnerServiceEventSource implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher publisher;

    @Override
    public void setApplicationEventPublisher(
        ApplicationEventPublisher applicationEventPublisher
    ) {
        this.publisher = applicationEventPublisher;
    }

    public void triggerEvent(PartnerServiceEvent event) {
        publisher.publishEvent(event);
    }
}
