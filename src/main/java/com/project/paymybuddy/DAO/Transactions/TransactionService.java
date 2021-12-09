package com.project.paymybuddy.DAO.Transactions;

import java.util.List;
import java.util.Optional;

public interface TransactionService {

    Iterable<TransactionEntity> findAllTransactions(Long id);

    List<TransactionEntity> findAllTransactionsByBeneficiaryEmail(String email);

    List<TransactionEntity> findAllTransactionsByPayerEmail(String email);

    Optional<TransactionEntity> findTransactionById(Long id);

    Optional<TransactionEntity> deleteTransactionByEmail(String email);

    TransactionEntity saveTransaction(TransactionEntity transactionEntity);

    Optional<TransactionEntity> updateTransaction(TransactionEntity transactionEntity, Long id);
}
