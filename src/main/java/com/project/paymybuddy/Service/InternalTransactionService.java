package com.project.paymybuddy.Service;

import com.project.paymybuddy.DTO.TransferRequest;
import com.project.paymybuddy.Entity.Transfers.TransferEntity;


public interface InternalTransactionService {

    TransferEntity DebitWalletToBankAccountTransfer(TransferRequest transferRequest);

    TransferEntity CreditWalletWithBankAccountTransfer(TransferRequest transferRequest);


}
