package com.project.paymybuddy.ServiceTest;

import com.project.paymybuddy.DAO.User.UserEntity;
import com.project.paymybuddy.DAO.User.UserRepository;
import com.project.paymybuddy.Domain.Service.Implementation.UserServiceImpl;
import com.project.paymybuddy.Domain.Util.EmailValidator;

import com.project.paymybuddy.Domain.DTO.RegistrationRequest;
import com.project.paymybuddy.Domain.Service.Implementation.RegistrationServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegistrationServiceTest {

    private UserEntity user = new UserEntity();
    private RegistrationRequest request = new RegistrationRequest();


    @Mock
    private UserRepository userRepository;
    @Mock
    private EmailValidator emailValidator;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @Mock
    private UserServiceImpl userService;
    @InjectMocks
    RegistrationServiceImpl registrationService;

    @BeforeEach
    public void setUp(){

        user = new UserEntity();
        user.setFirstname("Jean");
        user.setLastname("Test");
        user.setEmail("JeanTest@gmail.com");
        user.setCivility("M");
        user.setId(1L);
        user.setWallet(50);
        request.setCivility("M");
        request.setFirstname("Jean");
        request.setLastname("Test");
        request.setPassword("password");
        request.setEmail("JeanTest@gmail.com");



    }
    @Test
    public void registerTest(){
        lenient().when(emailValidator.test(any(String.class))).thenReturn(true);
        assertEquals("you sign up", registrationService.register(request));
    }
    @Test
    public void registerButEmailAlwaysExistTest(){
        assertThrows(IllegalStateException.class, () -> registrationService.register(request));
    }
    @Test
    public void signUpUserTest() {
        registrationService.signUpUser(user);
        verify(userRepository, times(1)).save(user);
        verify(userService, times(1)).enableAppUser(user.getEmail());
        verify(userService, times(1)).addRoleToUser(user.getEmail(), "ROLE_USER");
        assertEquals("you sign up", registrationService.signUpUser(user));
    }
    @Test
    public void signUpUserButAlwaysPresentTest(){
        lenient().when(userService.getUser("JeanTest@gmail.com")).thenReturn(user);
        assertThrows(IllegalStateException.class, () -> registrationService.signUpUser(user));
    }
}
