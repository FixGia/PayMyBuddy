package com.project.paymybuddy.Domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRequest {

    private String Civility;
    private String firstname;
    private String lastname;
    private String email;
    private String password;

}
