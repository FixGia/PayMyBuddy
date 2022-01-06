package com.project.paymybuddy.DAO.BankAccounts;

import com.project.paymybuddy.Domain.DTO.BankAccountRequest;

import java.util.Optional;

public interface BankAccountService {


    Iterable<BankAccountEntity> findAllBankAccountByUserEmail(String email);

    Optional<BankAccountEntity> findBankAccountByUserEmail(String email);

    Optional<BankAccountEntity> updateBankAccount(BankAccountEntity bankAccount);

    void deleteByUserEmail(String email);

    BankAccountEntity LinkUserToBankAccount(BankAccountRequest bankAccountRequest);
}
