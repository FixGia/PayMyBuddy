package com.project.paymybuddy.DAO.BankAccounts;

import java.util.Optional;

public interface BankAccountService {


    Iterable<BankAccountEntity> findAll();

    Optional<BankAccountEntity> findBankAccountById(Integer id);

Optional<BankAccountEntity> findBankAccountByUserId(Long id);

    Optional<BankAccountEntity> updateBankAccount(Integer id, BankAccountEntity bankAccount);

    void deleteById(Integer id);
}
