package com.project.paymybuddy.Domain.Service;


import com.project.paymybuddy.Domain.DTO.TransactionDTO;
import com.project.paymybuddy.DAO.Transactions.TransactionEntity;

import java.util.Optional;

public interface ExternalTransactionService {


    Optional<TransactionEntity> makeTransaction(TransactionDTO transactionDTO);
}
