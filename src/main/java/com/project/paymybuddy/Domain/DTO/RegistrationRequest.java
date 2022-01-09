package com.project.paymybuddy.Domain.DTO;


import lombok.Data;

@Data
public class RegistrationRequest {

    private String firstName;
    private String lastName;
    private String civility;
    private String password;
    private String email;

}
