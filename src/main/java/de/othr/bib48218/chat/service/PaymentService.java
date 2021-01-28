package de.othr.bib48218.chat.service;

import com.othr.swvigopay.entity.TransferDTO;
import com.othr.swvigopay.exceptions.TransferServiceExternalException;
import com.othr.swvigopay.service.TransferServiceExternalIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Service
class PaymentService implements IFPaymentService, TransferServiceExternalIF {

    @Value("${partner.service.payment.api-uri}")
    private String apiUri;

    @Autowired
    private RestTemplate restServiceClient;

    @Autowired
    @Qualifier("paymentHeaders")
    private HttpHeaders httpHeaders;

    @Override
    public ResponseEntity<TransferDTO> requestTransferExternal(TransferDTO transferDTO)
        throws RestClientException {
        final String path = "transfers/requestTransferExternal";
        HttpEntity<TransferDTO> request = new HttpEntity<>(transferDTO, httpHeaders);

        return restServiceClient
            .exchange(apiUri + path, HttpMethod.POST, request, TransferDTO.class);
    }

    @Override
    public void requestTransfer(TransferDTO transferDTO)
        throws TransferServiceExternalException {
        ResponseEntity<TransferDTO> response;
        try {
            response = requestTransferExternal(transferDTO);
            if (response.getStatusCode().isError()) {
                throw new TransferServiceExternalException("response error");
            }
        } catch (RestClientException e) {
            throw new TransferServiceExternalException("rest client exception");
        }
    }
}
