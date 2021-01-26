package de.othr.bib48218.chat.integration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

/**
 * A configuration providing http headers for rest request to partner services.
 */
@Configuration
class PartnerServiceConfig {

    @Value("${partner.service.payment.access-token}")
    private String paymentAccessToken;

    /**
     * @return the http headers containing required data (authentication,...) for payment service.
     */
    @Bean
    @Qualifier("paymentHeaders")
    public HttpHeaders getPaymentHttpHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("access-token", paymentAccessToken);
        return headers;
    }

}
