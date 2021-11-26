package com.project.paymybuddy.Login.registration;


import com.project.paymybuddy.Login.Email.EmailSender;
import com.project.paymybuddy.Login.EmailValidator;
import com.project.paymybuddy.model.User.UserEntity;
import com.project.paymybuddy.model.User.AppUserRole;
import com.project.paymybuddy.model.User.UserServiceImpl;
import com.project.paymybuddy.Login.token.ConfirmationToken;
import com.project.paymybuddy.Login.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RegistrationService {

private EmailValidator emailValidator;
private final UserServiceImpl userService;
private final ConfirmationTokenService confirmationTokenService;
private final DaoAuthenticationProvider daoAuthenticationProvider;

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
                request.getPassword(),
                AppUserRole.USER));
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
        return "confirmed";
    }

    public UserDTO signIn(@NotNull String userName,@NotNull String password){

        UserDetails userDetails = userService.loadUserByUsername(userName);
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(userDetails.getUsername());
        userEntity.setPassword(userDetails.getPassword());
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(30),
                userEntity);
        daoAuthenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(userName,password));

        return new UserDTO(userEntity.getEmail(), confirmationToken.getToken());
    }

}

