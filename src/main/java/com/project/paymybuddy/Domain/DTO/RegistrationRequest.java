package com.project.paymybuddy.Domain.DTO;


import lombok.Data;

@Data
public class RegistrationRequest {

    private String civility;
    private String firstname;
    private String lastname;
    private String password;
    private String email;

}
