package de.othr.bib48218.chat.service;

import com.othr.swvigopay.entity.TransferDTO;
import com.othr.swvigopay.exceptions.TransferServiceExternalException;
import com.othr.swvigopay.service.TransferServiceExternalIF;

/**
 * Wrapper interface of partner service, hiding redundant return values of {@link
 * TransferServiceExternalIF}
 */
public interface IFPaymentService extends TransferServiceExternalIF {

    /**
     * Request a certain payment transfer.
     *
     * @param transferDTO the data transfer object describing a transfer request.
     * @throws TransferServiceExternalException If unable to send transfer request to partner
     *                                          service
     */
    void requestTransfer(TransferDTO transferDTO) throws TransferServiceExternalException;

}
