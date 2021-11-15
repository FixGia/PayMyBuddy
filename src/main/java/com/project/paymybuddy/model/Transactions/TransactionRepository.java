package com.project.paymybuddy.model.Transactions;

import com.project.paymybuddy.model.Transactions.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}