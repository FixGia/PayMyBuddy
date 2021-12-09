package com.project.paymybuddy.DAO.Transactions;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {


    List<TransactionEntity> findTransactionEntitiesByBeneficiary_Email(String email);

    List<TransactionEntity> findTransactionEntitiesByPayer_Email(String email);


}