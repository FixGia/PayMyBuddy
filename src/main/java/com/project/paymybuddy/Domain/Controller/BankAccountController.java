package com.project.paymybuddy.Domain.Controller;

import com.project.paymybuddy.DAO.BankAccounts.BankAccountEntity;
import com.project.paymybuddy.DAO.BankAccounts.BankAccountService;
import com.project.paymybuddy.DAO.User.UserEntity;
import com.project.paymybuddy.DAO.User.UserService;
import com.project.paymybuddy.Domain.DTO.BankAccountRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@AllArgsConstructor
public class BankAccountController {

    private final BankAccountService bankAccountService;
    private final UserService userService;

    @PostMapping("api/user/bankaccount")
    public ResponseEntity<BankAccountEntity> linkUserToBankAccount(@RequestBody BankAccountRequest bankAccountRequest){

        BankAccountEntity bankAccountEntity = bankAccountService.LinkUserToBankAccount(bankAccountRequest);

        return ResponseEntity.ok(bankAccountEntity);
    }

    @GetMapping("api/user/bankaccount")
    public ResponseEntity<BankAccountEntity> getBankAccountByUser() {

        UserEntity currentUser = userService.getCurrentUser();
        Optional<BankAccountEntity> bankAccountEntity = bankAccountService.findBankAccountByUserEmail(currentUser.getEmail());
        return ResponseEntity.ok(bankAccountEntity.get());
    }

}
