package com.project.paymybuddy.Domain.Service;

import com.project.paymybuddy.Domain.DTO.TransferDTO;
import com.project.paymybuddy.DAO.Transfers.TransferEntity;


public interface InternalTransactionService {

    TransferEntity makeWalletDebitToBankAccountTransfer(TransferDTO transferDTO);

    TransferEntity makeWalletCreditToBankAccountTransfer(TransferDTO transferDTO);


}
