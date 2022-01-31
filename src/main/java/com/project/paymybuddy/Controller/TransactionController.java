package com.project.paymybuddy.Controller;

import com.project.paymybuddy.Entity.Transactions.TransactionEntity;
import com.project.paymybuddy.Service.TransactionService;
import com.project.paymybuddy.Entity.Transfers.TransferEntity;
import com.project.paymybuddy.Service.TransferService;
import com.project.paymybuddy.Entity.User.UserEntity;
import com.project.paymybuddy.DTO.TransactionRequest;
import com.project.paymybuddy.DTO.TransferRequest;
import com.project.paymybuddy.Service.ExternalTransactionService;
import com.project.paymybuddy.Service.InternalTransactionService;
import com.project.paymybuddy.Service.UserService;
import com.project.paymybuddy.Exception.DataNotFoundException;
import com.project.paymybuddy.Exception.NotConformDataException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@Slf4j
public class TransactionController {

    private UserService userService;
    private TransferService transferService;
    private TransactionService transactionService;
    private InternalTransactionService internalTransactionService;
    private ExternalTransactionService externalTransactionService;


    @GetMapping(value = {"/Transaction"})
    public String displayTransferAndTransaction(Model model) {

        UserEntity currentUser = userService.getCurrentUser();

        try {

            List<TransferEntity> transferEntityList = transferService.findAllByUser(userService.getCurrentUser());
            List<TransactionEntity> transactionEntityListByPayer = transactionService.findAllTransactionsByPayerEmail(currentUser.getEmail());
            List<TransactionEntity> transactionEntityListByBeneficiary = transactionService.findAllTransactionsByBeneficiaryEmail(currentUser.getEmail());

            model.addAttribute("transfers", transferEntityList);
            model.addAttribute("BeneficiaryTransactions", transactionEntityListByBeneficiary);
            model.addAttribute("PayerTransactions", transactionEntityListByPayer);

            log.info("Display All Transactions Belong to user {}", currentUser.getFirstname() + " " + currentUser.getLastname());
            return "/transactions";


        } catch (DataNotFoundException e) {
            log.error("Fail to display transactions Belong to user {}", currentUser.getFirstname() + " " + currentUser.getLastname());
            return "/Home";
        }
    }


    @GetMapping(value = {"/transfer/debit"})
    public String DisplayDebitFormTransfer(){

        return "DebitYourWallet";

    }

    @GetMapping(value = {"/transfer/credit"})
    public String DisplayCreditFormTransfer(){

        return "CreditYourWallet";

    }

    @PostMapping("/transfer/debit")
    public String CreditBankAccountTransaction(@ModelAttribute TransferRequest transferRequest, Model model){

        TransferEntity newTransaction = internalTransactionService.DebitWalletToBankAccountTransfer(transferRequest);
        new ResponseEntity<>(newTransaction, HttpStatus.OK);
        displayTransferAndTransaction(model);
        return "/transactions";
    }

    @PostMapping("/transfer/credit")
    public String DebitBankAccountTransaction(@ModelAttribute TransferRequest transferRequest, Model model){

        TransferEntity newTransaction = internalTransactionService.CreditWalletWithBankAccountTransfer(transferRequest);
        displayTransferAndTransaction(model);
        new ResponseEntity<>(newTransaction, HttpStatus.OK);
        return "/transactions";
    }

    @PostMapping("/makeTransaction")
    public String makeTransaction(@ModelAttribute TransactionRequest transactionRequest,Model model) {

        try {
            Optional<TransactionEntity> newTransaction = externalTransactionService.makeTransaction(transactionRequest);

            UserEntity currentUser = userService.getCurrentUser();
            List<TransferEntity> transferEntityList = transferService.findAllByUser(userService.getCurrentUser());
            List<TransactionEntity> transactionEntityListByPayer = transactionService.findAllTransactionsByPayerEmail(currentUser.getEmail());
            List<TransactionEntity> transactionEntityListByBeneficiary = transactionService.findAllTransactionsByBeneficiaryEmail(currentUser.getEmail());

            model.addAttribute("transfers", transferEntityList);
            model.addAttribute("BeneficiaryTransactions", transactionEntityListByBeneficiary);
            model.addAttribute("PayerTransactions", transactionEntityListByPayer);

            new ResponseEntity<>(newTransaction.get(), HttpStatus.OK);
            return "/transactions";
        } catch (NotConformDataException exception) {
            log.error("Can't make this transaction");
            return "FormTransaction";
        }
    }

    @GetMapping( value= { "/FormTransaction"})
    public String Transaction(Model model){
        model.addAttribute("currentUser", userService.getCurrentUser());
        model.addAttribute("contactList", userService.getCurrentUser().getContactList());
        displayTransferAndTransaction(model);
        return "/FormTransaction";
    }
}

