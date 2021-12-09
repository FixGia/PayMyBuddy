package com.project.paymybuddy.DAO.BankAccounts;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BankAccountEntityRepository extends JpaRepository<BankAccountEntity, Integer> {


    Optional<BankAccountEntity> findBankAccountEntityByUserEmail(String email);

    List<BankAccountEntity> findAllByUserEmail(String email);

    BankAccountEntity findBankAccountEntityByIban(String iban);


}