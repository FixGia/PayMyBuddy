package com.project.paymybuddy.DTO;

import com.project.paymybuddy.Util.GeneralConstraints;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class TransferRequest {

    @Digits(integer = 10, fraction = 2, message = "max digits 10 with 2 decimals")
    @Min(value = 0, message = "must be positive number")
    private double amount;

    @NotBlank(message = "FullName is mandatory")
    @Length(max = GeneralConstraints.VARIABLE_LENGTH_125,
            message = "The maximum length for FullName should"
                    + " be 125 characters")
    @Pattern(regexp = GeneralConstraints.PATTERN_ALPHANUMERIC,
            message = "Should be alphanumeric and minimum more"
                    + " than 2 characters")
    private String description;

}
