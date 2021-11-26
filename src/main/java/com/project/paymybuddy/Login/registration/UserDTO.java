package com.project.paymybuddy.Login.registration;

import com.project.paymybuddy.Login.token.ConfirmationToken;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {

    private String userName;
   private String token;
}
