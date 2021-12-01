package com.project.paymybuddy.Login.registration;


import com.project.paymybuddy.DAO.User.UserRepository;
import com.project.paymybuddy.DAO.User.UserEntity;
import com.project.paymybuddy.DAO.User.AppUserRole;
import com.project.paymybuddy.DAO.User.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

private EmailValidator emailValidator;
private final UserRepository userRepository;
private final BCryptPasswordEncoder bCryptPasswordEncoder;

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
                request.getPassword(),
                AppUserRole.USER));
        return token;
    }


    /**
     * User signUpUser
     * <p>
     * use to signUp new user and deliver a confirmation token
     * @param userEntity
     * @return message
     */
    public String signUpUser(@NotNull UserEntity userEntity) {
        boolean userExists = userRepository.findUserEntityByEmail(userEntity.getEmail()).isPresent();
        if (userExists) {
            throw new IllegalStateException("email not valid");
        }
        String encodedPassword = bCryptPasswordEncoder.encode(userEntity.getPassword());
        userEntity.setPassword(encodedPassword);
        userEntity.setEnabled(true);
        userRepository.save(userEntity);

        return "User is signUp";
    }

}

