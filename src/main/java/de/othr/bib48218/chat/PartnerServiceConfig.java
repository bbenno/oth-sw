package de.othr.bib48218.chat;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
public class PartnerServiceConfig {

    @Value("${partner.service.payment.access-token}")
    private String paymentAccessToken;

    @Bean
    @Qualifier("paymentHeaders")
    public HttpHeaders getPaymentHttpHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("access-token", paymentAccessToken);
        return headers;
    }

}
