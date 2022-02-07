package com.project.paymybuddy.DTO;


import com.project.paymybuddy.Util.GeneralConstraints;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class RegistrationRequest {

    @Length(max= GeneralConstraints.VARIABLE_LENGTH_30, message = "Max Length must be 30 characters")
    @NotBlank(message = " civility is mandatory")
    private String civility;

    @Length(max=GeneralConstraints.VARIABLE_LENGTH_30, message = "Max Length must be 30 characters")
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
    @NotBlank(message = " password is mandatory")
    @Pattern(regexp = GeneralConstraints.PATTERN_PASSWORD,
            message = "The password must contain at least"
                    + " 8 characters that includes"
                    + " any one uppercase letter,"
                    + " any one number and"
                    + " any one symbol ( & ~ # @ = * - + € ^ $ £ µ % )")
    private String password;

    @NotBlank(message = " email is mandatory")
    @Length(max=GeneralConstraints.VARIABLE_LENGTH_30, message = "Max Length must be 30 characters")
    @Pattern(regexp = GeneralConstraints.PATTERN_USERNAME, message = "Must be a email address")
    private String email;

}
