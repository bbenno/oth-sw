package de.othr.bib48218.chat.service;

import com.othr.swvigopay.entity.TransferDTO;
import com.othr.swvigopay.exceptions.TransferServiceExternalException;
import com.othr.swvigopay.service.TransferServiceExternalIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PaymentService implements TransferServiceExternalIF {

    @Value("${partner.service.payment.port}")
    private int port;

    @Value("${partner.service.payment.api-path}")
    private String apiPath;

    @Autowired
    private RestTemplate restServiceClient;

    protected String getAPIUrl() {
        return "http://localhost/" + port + "/" + apiPath;
    }

    @Override
    public ResponseEntity<TransferDTO> requestTransferExternal(
        TransferDTO transferDTO
    ) throws TransferServiceExternalException {
        return null;
    }

}
