package com.project.paymybuddy.DAO.Transactions;

import java.util.Optional;

public interface TransactionService {

    Iterable<TransactionEntity> findAllTransactions(Long id);

    Optional<TransactionEntity> findTransactionById(Long id);

    Optional<TransactionEntity> deleteTransaction(Long id);

    TransactionEntity saveTransaction(TransactionEntity transactionEntity);

    Optional<TransactionEntity> updateTransaction(TransactionEntity transactionEntity, Long id);
}
