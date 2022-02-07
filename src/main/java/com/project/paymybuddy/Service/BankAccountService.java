package com.project.paymybuddy.Service;

import com.project.paymybuddy.DTO.BankAccountRequest;
import com.project.paymybuddy.Entity.BankAccounts.BankAccountEntity;

import java.util.Optional;

public interface BankAccountService {


    Iterable<BankAccountEntity> findAllBankAccountByUserEmail(String email);

    Optional<BankAccountEntity> findBankAccountByUserEmail(String email);

    Optional<BankAccountEntity> updateBankAccount(BankAccountEntity bankAccount);

    void deleteByUserEmail(String email);

    BankAccountEntity LinkUserToBankAccount(BankAccountRequest bankAccountRequest);
}
