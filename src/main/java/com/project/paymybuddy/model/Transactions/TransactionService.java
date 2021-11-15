package com.project.paymybuddy.model.Transactions;

import com.project.paymybuddy.model.Transactions.Transaction;

import java.util.Optional;

public interface TransactionService {

    Iterable<Transaction> findAllTransactions(Long id);

    Optional<Transaction> findTransactionByContact(String Beneficiary);

    Optional<Transaction> deleteTransaction(Long id);

    Optional<Transaction> madeTransaction();


}
