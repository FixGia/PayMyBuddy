package com.project.paymybuddy.Domain.DTO;

import com.project.paymybuddy.model.User.UserEntity;
import lombok.*;


@Data
public class TransactionDTO {

    private Long id;
    private double amount;
    private double commission;
    private UserEntity beneficiary;
    private UserEntity payer;
    private String description;
}
