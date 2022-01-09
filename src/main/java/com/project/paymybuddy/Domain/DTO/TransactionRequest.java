package com.project.paymybuddy.Domain.DTO;

import lombok.*;


@Data
public class TransactionRequest {


    private double amount;
    private String beneficiary;
    private String payer;
    private String description;
}
