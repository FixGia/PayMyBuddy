package com.project.paymybuddy.Domain.DTO;

import lombok.*;

@Data
public class BankAccountRequest {

    private double Amount;
    private String Iban;

}
