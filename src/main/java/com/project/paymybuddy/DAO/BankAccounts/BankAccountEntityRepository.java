package com.project.paymybuddy.DAO.BankAccounts;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankAccountEntityRepository extends JpaRepository<BankAccountEntity, Integer> {


    Optional<BankAccountEntity> findBankAccountEntityByUserId(Long id);
}