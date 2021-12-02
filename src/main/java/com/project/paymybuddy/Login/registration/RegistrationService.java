package com.project.paymybuddy.Login.registration;


import com.project.paymybuddy.model.User.RoleRepository;
import com.project.paymybuddy.model.User.UserEntity;
import com.project.paymybuddy.model.User.UserServiceImpl;
import com.project.paymybuddy.Login.registration.token.ConfirmationToken;
import com.project.paymybuddy.Login.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

private EmailValidator emailValidator;
private final UserServiceImpl userService;
private final RoleRepository roleRepository;
private final ConfirmationTokenService confirmationTokenService;

    public String register(@NotNull RegistrationRequest request){

        boolean isValidEmail = emailValidator.test(request.getEmail());
        if (!isValidEmail){
            throw new IllegalStateException("email not valid");
        }
        String token = userService.signUpUser(new UserEntity(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getCivility(),
                request.getPassword()));
        return token;
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiredAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        userService.enableAppUser(
                confirmationToken.getUserEntity().getEmail());
        userService.addRoleToUser(confirmationToken.getUserEntity().getEmail(),"ROLE_USER");
        return "confirmed";
    }


}

