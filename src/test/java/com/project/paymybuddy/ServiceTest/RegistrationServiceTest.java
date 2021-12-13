package com.project.paymybuddy.ServiceTest;

import com.project.paymybuddy.DAO.User.UserRepository;
import com.project.paymybuddy.DAO.User.UserServiceImpl;
import com.project.paymybuddy.Registration.EmailValidator;
import com.project.paymybuddy.Registration.RegistrationService;
import com.project.paymybuddy.Registration.token.ConfirmationTokenService;
import com.project.paymybuddy.Security.PasswordEncoder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

@ExtendWith(MockitoExtension.class)
public class RegistrationServiceTest {

    @Mock
    private ConfirmationTokenService confirmationTokenService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private EmailValidator emailValidator;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserServiceImpl userService;
    @InjectMocks
    RegistrationService registrationService;


    @Test
    public void registerTest(){

    }
}
