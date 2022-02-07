package com.project.paymybuddy.Service;

import com.project.paymybuddy.Entity.Transactions.TransactionEntity;

import java.util.List;
import java.util.Optional;

public interface TransactionService {


    List<TransactionEntity> findAllTransactionsByBeneficiaryEmail(String email);

    List<TransactionEntity> findAllTransactionsByPayerEmail(String email);

    TransactionEntity saveTransaction(TransactionEntity transactionEntity);

    Optional<TransactionEntity> saveNewTransaction(Long id, TransactionEntity transactionEntity);

    Iterable<TransactionEntity> findAllTransactions(Long id);

    TransactionEntity findTransactionById(Long id);

}
