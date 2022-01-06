package com.project.paymybuddy.ServiceTest;

import com.project.paymybuddy.DAO.User.UserEntity;
import com.project.paymybuddy.DAO.User.UserRepository;
import com.project.paymybuddy.DAO.User.UserServiceImpl;
import com.project.paymybuddy.Registration.EmailValidator;
import com.project.paymybuddy.Registration.RegistrationRequest;
import com.project.paymybuddy.Registration.RegistrationService;
import com.project.paymybuddy.Registration.token.ConfirmationTokenService;
import com.project.paymybuddy.Security.PasswordEncoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegistrationServiceTest {

    private UserEntity user;


    @Mock
    private ConfirmationTokenService confirmationTokenService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private EmailValidator emailValidator;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @Mock
    private UserServiceImpl userService;
    @InjectMocks
    RegistrationService registrationService;

    @BeforeEach
    public void setUp(){

        user = new UserEntity();
        user.setFirstname("Jean");
        user.setLastname("Test");
        user.setEmail("JeanTest@gmail.com");
        user.setCivility("M");
        user.setId(1L);
        user.setWallet(50);




    }
    @Test
    public void registerTest(){
        RegistrationRequest request = new RegistrationRequest("Jean", "Test", "M", "password", "JeanTest@gmail.com");
        when(emailValidator.test("JeanTest@gmail.com")).thenReturn(true);
        registrationService.register(request);
        when(registrationService.signUpUser(new UserEntity())).thenReturn("token");



    }
}
