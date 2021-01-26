package de.othr.bib48218.chat.integration;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

/**
 * The source of {@link PartnerServiceEvent}s.
 */
@Component
public class PartnerServiceEventSource implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher publisher;

    /**
     * Sets the application event publisher.
     *
     * @param applicationEventPublisher the application event publisher
     */
    @Override
    public void setApplicationEventPublisher(
        ApplicationEventPublisher applicationEventPublisher
    ) {
        this.publisher = applicationEventPublisher;
    }

    /**
     * Triggers a certain partner service event.
     *
     * @param event the partner service event
     */
    public void triggerEvent(PartnerServiceEvent event) {
        new Thread(() -> publisher.publishEvent(event)).start();
    }
}
