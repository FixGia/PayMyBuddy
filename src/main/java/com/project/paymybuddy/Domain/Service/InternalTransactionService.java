package com.project.paymybuddy.Domain.Service;

import com.project.paymybuddy.Domain.DTO.TransferRequest;
import com.project.paymybuddy.DAO.Transfers.TransferEntity;


public interface InternalTransactionService {

    TransferEntity DebitWalletToBankAccountTransfer(TransferRequest transferRequest);

    TransferEntity CreditWalletWithBankAccountTransfer(TransferRequest transferRequest);


}
