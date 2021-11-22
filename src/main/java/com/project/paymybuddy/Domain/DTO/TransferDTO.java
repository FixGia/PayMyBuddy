package com.project.paymybuddy.Domain.DTO;

import com.project.paymybuddy.model.BankAccounts.BankAccountEntity;
import com.project.paymybuddy.model.User.UserEntity;
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
