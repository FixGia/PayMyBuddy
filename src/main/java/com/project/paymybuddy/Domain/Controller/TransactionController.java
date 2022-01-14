package com.project.paymybuddy.Domain.Controller;

import com.project.paymybuddy.DAO.Transactions.TransactionEntity;
import com.project.paymybuddy.DAO.Transactions.TransactionService;
import com.project.paymybuddy.DAO.Transfers.TransferEntity;
import com.project.paymybuddy.DAO.Transfers.TransferService;
import com.project.paymybuddy.DAO.User.UserEntity;
import com.project.paymybuddy.Domain.Service.UserService;
import com.project.paymybuddy.Exception.DataNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
@Slf4j
public class TransactionController {

    private UserService userService;
    private TransferService transferService;
    private TransactionService transactionService;


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

        @GetMapping( value = {"/deleteTransaction"})
        public String deleteTransaction(@RequestParam Long id) {

        transactionService.deleteTransaction(transactionService.findTransactionById(id));
            return "/transactions";

        }

        @GetMapping (value = {"/deleteTransfer"})
    public String deleteTransfer(@RequestParam Long id){
        transferService.deleteTransfer(id);
        return "/transactions";
        }
    }

