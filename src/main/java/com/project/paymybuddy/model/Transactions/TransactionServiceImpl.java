package com.project.paymybuddy.model.Transactions;


import java.util.Optional;

public class TransactionServiceImpl implements TransactionService {
    @Override
    public Iterable<Transaction> findAllTransactions(Long id) {
        return null;
    }

    @Override
    public Optional<Transaction> findTransactionByContact(String Beneficiary) {
        return Optional.empty();
    }

    @Override
    public Optional<Transaction> deleteTransaction(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Transaction> madeTransaction() {
        return Optional.empty();
    }
}




