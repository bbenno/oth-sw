package de.othr.bib48218.chat;

import de.othr.bib48218.chat.entity.ServiceType;
import de.othr.bib48218.chat.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventSubscriber implements ApplicationListener<PartnerServiceEvent> {

    @Autowired
    private PaymentService paymentService;

    @Override
    public void onApplicationEvent(PartnerServiceEvent event) {
        if (event.getServiceType() == ServiceType.PAYMENT) {
            paymentService.test();
        }
    }
}
