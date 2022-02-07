package com.project.paymybuddy.ServiceTest;

import com.project.paymybuddy.Entity.User.UserEntity;
import com.project.paymybuddy.Entity.User.UserRepository;
import com.project.paymybuddy.Service.UserService;
import com.project.paymybuddy.Service.Implementation.ContactServiceImpl;
import com.project.paymybuddy.Exception.DataNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ContactServiceTest {

    private final UserEntity user = new UserEntity();
    private final UserEntity addUser = new UserEntity();
    private final List<UserEntity> contactList = new ArrayList<>();
    private DataNotFoundException dataNotFoundException;

    @Mock
    UserRepository userRepository;
    @Mock
    UserService userService;
    @InjectMocks
    ContactServiceImpl contactService;

    @BeforeEach
    public void setUp(){

        user.setId(1L);
        user.setEmail("JeanTest@gmail.com");
        user.setPassword("password");
        user.setCivility("M");
        user.setWallet(5000);
        user.setFirstname("Jean");
        user.setLastname("Test");
        user.setEnabled(true);
        addUser.setId(2L);
        addUser.setEmail("PetitTest@gmail.com");
        addUser.setPassword("password");
        addUser.setCivility("M");
        addUser.setWallet(2000);
        addUser.setFirstname("Petit");
        addUser.setLastname("Test");
        addUser.setEnabled(true);
        lenient().when(userService.getCurrentUser()).thenReturn(user);
        lenient().when(userService.saveUser(user)).thenReturn(user);
        lenient().when(userService.saveUser(addUser)).thenReturn(addUser);
        lenient().when(userService.getUser("PetitTest@gmail.com")).thenReturn(addUser);

    }

    @Test
    public void TestAddContact(){
        user.setContactList(contactList);
        contactService.addContact("PetitTest@gmail.com");
        assertTrue(user.getContactList().contains(addUser));
        verify(userService, times(1)).updateUsers(user);
    }
    @Test
    public void TestAddContactButContactDoesntExist(){
        user.setContactList(contactList);
        contactService.addContact("NexistePas@gmail.com");
        assertFalse(user.getContactList().contains(addUser));
        verify(userRepository, times(0)).save(user);
    }
    @Test
    public void TestFindAContactBelongUser(){
        user.setContactList(contactList);
        contactList.add(addUser);
        contactService.findAContactBelongUser("PetitTest@gmail.com");
        verify(userService, times(1)).getUser("PetitTest@gmail.com");
        assertTrue(user.getContactList().contains(addUser));
    }

    @Test
    public void TestDeleteContactInContactList(){
        user.setContactList(contactList);
        contactList.add(addUser);
        contactService.deleteContactInContactList("PetitTest@gmail.com");
        verify(userService, times(1)).updateUsers(user);
        assertTrue(contactList.isEmpty());
    }

    @Test
    public void TestFindEveryContactBelongUser(){
        user.setContactList(contactList);
        contactList.add(addUser);
        contactService.findEveryContactBelongUser();
    }

}
