package com.project.paymybuddy.Registration;

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
