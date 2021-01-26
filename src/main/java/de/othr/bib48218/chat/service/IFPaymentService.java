package de.othr.bib48218.chat.service;

import com.othr.swvigopay.entity.TransferDTO;
import com.othr.swvigopay.exceptions.TransferServiceExternalException;
import com.othr.swvigopay.service.TransferServiceExternalIF;

public interface IFPaymentService extends TransferServiceExternalIF {

    TransferDTO requestTransfer(TransferDTO transferDTO) throws TransferServiceExternalException;

}
