package com.project.paymybuddy.Controller;

import com.project.paymybuddy.Entity.BankAccounts.BankAccountEntity;
import com.project.paymybuddy.Service.BankAccountService;
import com.project.paymybuddy.DTO.BankAccountRequest;
import com.project.paymybuddy.Service.UserService;
import com.project.paymybuddy.Exception.NotConformDataException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@Slf4j
public class BankAccountController {

    private final BankAccountService bankAccountService;
    private final UserService userService;

    @PostMapping(value= {"/addBankAccount"})
    public String linkUserToBankAccount(@ModelAttribute BankAccountRequest bankAccountRequest, Model model) {

        try {
            BankAccountEntity bankAccountEntity = bankAccountService.LinkUserToBankAccount(bankAccountRequest);
            ResponseEntity.ok(bankAccountEntity);
            model.addAttribute("currentUser", userService.getCurrentUser());
            log.info("Request addBankAccount was a success");
            return "Home";

        } catch (NotConformDataException exception) {
            exception.printStackTrace();
            log.error("Request addBankAccount was a fail");
            return "FormBankAccount";
        }
    }

    @GetMapping(value ={"/BankAccount"})
    public String linkBankAccountForUser(Model model) {
        model.addAttribute("currentUser", userService.getCurrentUser());
        return "FormBankAccount";
    }

}
