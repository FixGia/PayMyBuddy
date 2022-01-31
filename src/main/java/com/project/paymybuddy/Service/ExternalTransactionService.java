package com.project.paymybuddy.Service;


import com.project.paymybuddy.DTO.TransactionRequest;
import com.project.paymybuddy.Entity.Transactions.TransactionEntity;

import java.util.List;
import java.util.Optional;

public interface ExternalTransactionService {

    Optional<TransactionEntity> makeTransaction(TransactionRequest transactionRequest);

    List<TransactionEntity> displayedTransactionWhenUserIsBeneficiary();

    List<TransactionEntity> displayedTransactionWhenUserIsPayer();
}
