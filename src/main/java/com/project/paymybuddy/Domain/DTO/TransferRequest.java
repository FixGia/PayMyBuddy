package com.project.paymybuddy.Domain.DTO;

import com.project.paymybuddy.DAO.BankAccounts.BankAccountEntity;
import com.project.paymybuddy.DAO.User.UserEntity;
import lombok.*;

@Data
public class TransferRequest {

    private double amount;
    private String description;

}
