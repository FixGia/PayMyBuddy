package com.project.paymybuddy.Domain.Controller;

import com.project.paymybuddy.DAO.Transfers.TransferEntity;
import com.project.paymybuddy.DAO.Transfers.TransferService;
import com.project.paymybuddy.DAO.User.UserEntity;
import com.project.paymybuddy.Domain.Service.UserService;
import com.project.paymybuddy.Domain.DTO.TransferRequest;
import com.project.paymybuddy.Domain.Service.InternalTransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class InternalTransactionController {

private final InternalTransactionService internalTransactionService;
private final TransferService transferService;
private final UserService userService;


    @GetMapping(value = {"/transfer/debit"})
    public String DisplayDebitFormTransfer(){
        return "DebitYourWallet";
    }

    @GetMapping(value = {"/transfer/credit"})
    public String DisplayCreditFormTransfer(){
        return "CreditYourWallet";
    }

    @PostMapping("/transfer/debit")
    public String CreditBankAccountTransaction(@ModelAttribute TransferRequest transferRequest){

        TransferEntity newTransaction = internalTransactionService.DebitWalletToBankAccountTransfer(transferRequest);
        new ResponseEntity<>(newTransaction, HttpStatus.OK);
        return "/Profile";
    }

    @PostMapping("/transfer/credit")
    public ResponseEntity<TransferEntity> DebitBankAccountTransaction(@ModelAttribute TransferRequest transferRequest){

        TransferEntity newTransaction = internalTransactionService.CreditWalletWithBankAccountTransfer(transferRequest);

        return new ResponseEntity<>(newTransaction, HttpStatus.OK);
    }

}
