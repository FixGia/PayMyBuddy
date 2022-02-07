package com.project.paymybuddy.DTO;

import com.project.paymybuddy.Util.GeneralConstraints;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {

    @Length(max= GeneralConstraints.VARIABLE_LENGTH_30, message = "Max Length must be 30 characters")
    @Pattern(regexp = GeneralConstraints.PATTERN_ALPHABET_CHARACTERS,
            message = "must be alphanumeric, minimum 2 characters")
    @NotBlank(message = " firstname is mandatory")
    private String firstname;

    @Length(max=GeneralConstraints.VARIABLE_LENGTH_30, message = "Max Length must be 30 characters")
    @Pattern(regexp = GeneralConstraints.PATTERN_ALPHABET_CHARACTERS,
            message = "must be alphanumeric, minimum 2 characters")
    @NotBlank(message = " lastname is mandatory")
    private String lastname;

    @Length(max=GeneralConstraints.VARIABLE_LENGTH_30, message = "Max Length must be 30 characters")
    @NotBlank(message = " email is mandatory")
    @Pattern(regexp = GeneralConstraints.PATTERN_USERNAME)
    private String email;

    @Length(max=GeneralConstraints.VARIABLE_LENGTH_30, message = "Max Length must be 30 characters")
    @NotBlank(message = " password is mandatory")
    @Pattern(regexp = GeneralConstraints.PATTERN_PASSWORD,
            message = "The password must contain at least"
                    + " 8 characters that includes"
                    + " any one uppercase letter,"
                    + " any one number and"
                    + " any one symbol ( & ~ # @ = * - + € ^ $ £ µ % )")
    private String password;

    @Digits(integer = 10, fraction = 2, message = "max digits 10 with 2 decimals")
    @Min(value = 0, message = "must be positive number")
    private Double Wallet;


}
