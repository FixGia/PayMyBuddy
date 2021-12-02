package com.project.paymybuddy.Login.registration;

import com.project.paymybuddy.model.User.Role;
import lombok.*;


@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Setter
public class RegistrationRequest {

    private final String firstName;
    private final String lastName;
    private final String civility;
    private final String password;
    private final String email;

}
