package com.project.paymybuddy.Domain.Controller;

import com.project.paymybuddy.DAO.BankAccounts.BankAccountEntity;
import com.project.paymybuddy.DAO.BankAccounts.BankAccountService;
import com.project.paymybuddy.Domain.DTO.BankAccountRequest;
import com.project.paymybuddy.Exception.NotConformDataException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@Slf4j
public class BankAccountController {

    private final BankAccountService bankAccountService;

    @PostMapping(value= {"/addBankAccount"})
    public String linkUserToBankAccount(@ModelAttribute BankAccountRequest bankAccountRequest) {

        try {
            BankAccountEntity bankAccountEntity = bankAccountService.LinkUserToBankAccount(bankAccountRequest);
            ResponseEntity.ok(bankAccountEntity);
            log.info("Request addBankAccount was a success");
            return "/Home";
        } catch (NotConformDataException exception) {
            exception.printStackTrace();
            log.error("Request addBankAccount was a fail");
            return "FormBankAccount";
        }
    }

    @GetMapping(value ={"/BankAccount"})
    public String linkBankAccountForUser() {
        return "FormBankAccount";
    }

}
