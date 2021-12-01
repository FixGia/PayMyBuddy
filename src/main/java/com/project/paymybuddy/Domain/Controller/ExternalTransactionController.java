package com.project.paymybuddy.Domain.Controller;

import com.project.paymybuddy.Domain.DTO.TransactionDTO;
import com.project.paymybuddy.Domain.Service.ExternalTransactionService;
import com.project.paymybuddy.DAO.Transactions.TransactionEntity;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@AllArgsConstructor
@RestController
public class ExternalTransactionController {

    ExternalTransactionService externalTransactionService;

    @PostMapping("/transaction")
    public ResponseEntity<TransactionEntity> makeTransaction(TransactionDTO transactionDTO){

        Optional<TransactionEntity> newTransaction = externalTransactionService.makeTransaction(transactionDTO);

        return new ResponseEntity<>(newTransaction.get(), HttpStatus.OK);
    }
}
