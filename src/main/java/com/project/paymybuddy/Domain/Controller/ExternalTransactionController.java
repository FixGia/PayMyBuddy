package com.project.paymybuddy.Domain.Controller;

import com.project.paymybuddy.Domain.DTO.TransactionRequest;
import com.project.paymybuddy.Domain.Service.ExternalTransactionService;
import com.project.paymybuddy.DAO.Transactions.TransactionEntity;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
public class ExternalTransactionController {

    private ExternalTransactionService externalTransactionService;



    @PostMapping("api/user/transaction")
    public ResponseEntity<TransactionEntity> makeTransaction(@RequestBody TransactionRequest transactionRequest){


        Optional<TransactionEntity> newTransaction = externalTransactionService.makeTransaction(transactionRequest);

        return new ResponseEntity<>(newTransaction.get(), HttpStatus.OK);
    }

    @GetMapping("api/user/transaction/beneficiary")
    public ResponseEntity<List<TransactionEntity>> displayedTransactionByBeneficiary(){

        List<TransactionEntity> transactionEntities = externalTransactionService.displayedTransactionWhenUserIsBeneficiary();
        return new ResponseEntity<>(transactionEntities, HttpStatus.OK);
    }

    @GetMapping("api/user/transaction/payer")
    public ResponseEntity<List<TransactionEntity>> displayedTransactionByPayer() {

        List<TransactionEntity> transactionEntities = externalTransactionService.displayedTransactionWhenUserIsPayer();

        return new ResponseEntity<>(transactionEntities, HttpStatus.OK);
    }

}
