package com.project.paymybuddy.Domain.Service.Implementation;


import com.project.paymybuddy.DAO.User.UserEntity;
import com.project.paymybuddy.DAO.User.UserRepository;
import com.project.paymybuddy.Domain.DTO.RegistrationRequest;
import com.project.paymybuddy.Domain.Service.RegistrationService;
import com.project.paymybuddy.Domain.Util.EmailValidator;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {


    private EmailValidator emailValidator;
    private final UserServiceImpl userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     *
     * @param request RegistrationRequest contain every information to registration
     * @return a string "you sign up"
     *
     */
    public String register(@NotNull RegistrationRequest request){

        boolean isValidEmail = emailValidator.test(request.getEmail());
        if (!isValidEmail){
            throw new IllegalStateException("email not valid");
        }
        String token = signUpUser(new UserEntity(
                request.getFirstname(),
                request.getLastname(),
                request.getEmail(),
                request.getCivility(),
                request.getPassword()));
        return token;
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
            throw new IllegalStateException("email already exist in DB");
        }

        String encodedPassword = passwordEncoder.encode(userEntity.getPassword());
        userEntity.setPassword(encodedPassword);
        userRepository.save(userEntity);
        userService.enableAppUser(userEntity.getEmail());
        userService.addRoleToUser(userEntity.getEmail(), "ROLE_USER");
        return "you sign up";
    }




}

