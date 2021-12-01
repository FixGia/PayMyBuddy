package com.project.paymybuddy.Domain.DTO;

import com.project.paymybuddy.DAO.BankAccounts.BankAccountEntity;
import com.project.paymybuddy.DAO.User.UserEntity;
import lombok.*;

@Data
public class TransferDTO {

    private Long id ;
    private double amount;
    private double credit;
    private double debit;
    private String description;
    private UserEntity user;
    private BankAccountEntity bankAccount;

}
