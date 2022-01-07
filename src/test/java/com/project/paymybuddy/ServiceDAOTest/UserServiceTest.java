package com.project.paymybuddy.ServiceDAOTest;

import com.project.paymybuddy.DAO.User.*;
import com.project.paymybuddy.Security.PasswordEncoder;
import com.sun.xml.bind.v2.TODO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private UserEntity user = new UserEntity();
    private Role role = new Role();
    private Authentication authentication;

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @InjectMocks
    UserServiceImpl userService;
    @Mock
    UserDetailsService userDetailsService;

    @BeforeEach
    public void SetUp(){

        user = new UserEntity();
        user.setFirstname("Jean");
        user.setLastname("Test");
        user.setEmail("JeanTest@gmail.com");
        user.setCivility("M");
        user.setId(1L);
        user.setWallet(50);
        user.setPassword("password");

        role = new Role();
        role.setId(1L);
        role.setName("User_Role");



        lenient().when(userRepository.save(user)).thenReturn(user);

        authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.lenient().when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }



    @Test
    public void enableAppUserTest() {
        userService.enableAppUser(user.getEmail());
        verify(userRepository, times(1)).enableAppUser(user.getEmail());

    }
    @Test
    public void updateUsersTest(){

        lenient().when(userRepository.findByEmail("JeanTest@gmail.com")).thenReturn(user);
        if (user != null) {
            userService.updateUsers(user);
            verify(userRepository, times(1)).save(user);
        }
        else {
            verify(userRepository, times(0)).save(user);
        }
    }

    @Test
    public void findUsersByIdTest(){
        userService.findUsersById(user.getId());
        verify(userRepository, times(1)).findById(1L);
    }
    @Test
    public void deleteUserByEmailTest(){
        userService.deleteUserByEmail(user.getEmail());
        verify(userRepository, times(1)).deleteByEmail(user.getEmail());
    }
    @Test
    public void updateUserWalletTest(){
        lenient().when(userRepository.findById(user.getId())).thenReturn(Optional.ofNullable(user));
        userService.updateUserWallet(user.getId(), user);
        verify(userRepository, times(1)).save(user);

    }

    @Test
    public void saveROleTest(){
        userService.saveRole(role);
        verify(roleRepository, times(1)).save(role);
    }
    @Test
    public void addRoleToUserTest(){
        lenient().when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
        userService.addRoleToUser(user.getEmail(), role.getName());
        verify(userRepository,times(1)).findByEmail(user.getEmail());
        verify(roleRepository,times(1)).findByName(role.getName());

    }
    @Test
    public void getUserTest(){
        lenient().when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
        userService.getUser(user.getEmail());
        verify(userRepository, times(1)).findByEmail(user.getEmail());
        assertEquals(user,userRepository.findByEmail(user.getEmail()));
    }
    @Test
    public void getUsersTest(){
        userService.getUsers();
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void saveUserTest(){
        userService.saveUser(user);
        verify(userRepository, times(1)).save(user);

    }

    @Test
    public void getCurrentUserTest(){
        lenient().when(authentication.getName()).thenReturn("JeanTest@gmail.com");
        lenient().when(userRepository.findByEmail("JeanTest@gmail.com")).thenReturn(user);
        assertEquals( "Test", userService.getCurrentUser().getLastname());
    }

    @Test
    public void loadUserByUsernameTest() {
        lenient().when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
        assertEquals("JeanTest@gmail.com", userService.loadUserByUsername(user.getEmail()).getUsername());
    }
}
