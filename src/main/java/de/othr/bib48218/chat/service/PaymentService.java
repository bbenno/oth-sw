package de.othr.bib48218.chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PaymentService {

    @Value("${partner.service.payment.port}")
    private int port;

    @Value("${partner.service.payment.api-path}")
    private String apiPath;

    @Autowired
    private RestTemplate restServiceClient;

    public void test() {
    }

    protected String getAPIUrl() {
        return "http://localhost/" + port + "/" + apiPath;
    }
}
