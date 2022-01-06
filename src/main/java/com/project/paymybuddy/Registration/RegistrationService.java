package com.project.paymybuddy.Registration;


import com.project.paymybuddy.DAO.User.UserEntity;
import com.project.paymybuddy.DAO.User.UserRepository;
import com.project.paymybuddy.DAO.User.UserServiceImpl;
import com.project.paymybuddy.Registration.token.ConfirmationToken;
import com.project.paymybuddy.Registration.token.ConfirmationTokenService;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String register(@NotNull RegistrationRequest request){

        boolean isValidEmail = emailValidator.test(request.getEmail());
        if (!isValidEmail){
            throw new IllegalStateException("email not valid");
        }
        String token = signUpUser(new UserEntity(
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
            confirmationTokenService.DeleteToken(token);
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiredAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            confirmationTokenService.DeleteToken(token);
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        userService.enableAppUser(
                confirmationToken.getUserEntity().getEmail());
        userService.addRoleToUser(confirmationToken.getUserEntity().getEmail(),"ROLE_USER");
        confirmationTokenService.DeleteToken(token);
        return "confirmed";
    }

    /**
     * User signUpUser
     * <p>
     * use to signUp new user and deliver a confirmation token
     *
     * @param userEntity
     * @return new token
     */
    public String signUpUser(@NotNull UserEntity userEntity) {
        UserEntity userExists =
                userService.getUser(userEntity.getEmail());
        if (userExists != null) {
            throw new IllegalStateException("email not valid");
        }
        String encodedPassword = passwordEncoder.encode(userEntity.getPassword());

        userEntity.setPassword(encodedPassword);
        userRepository.save(userEntity);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                userEntity);
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        return token;
    }


}

