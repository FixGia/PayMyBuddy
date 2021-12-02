package com.project.paymybuddy.Domain.Controller;

import com.project.paymybuddy.Domain.Service.ContactService;
import com.project.paymybuddy.Login.registration.RegistrationService;
import com.project.paymybuddy.Login.registration.UserDTO;
import com.project.paymybuddy.model.User.UserEntity;
import com.project.paymybuddy.model.User.UserRepository;
import com.project.paymybuddy.model.User.UserService;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.http.parser.Authorization;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")

public class ContactController {

    ContactService contactService;
    RegistrationService registrationService;
    UserRepository userRepository;
    UserService userService;
    AuthenticationManagerBuilder authenticationManagerBuilder;




    @GetMapping("/contacts")
    public ResponseEntity<List<UserEntity>> displayedContactList() throws Exception {

        List<UserEntity> contactList = contactService.findEveryContactBelongUser();
            return new ResponseEntity<>(contactList, HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<List<UserEntity>> addContactToContactList(UserDTO userDTO, String email){

        List<UserEntity> contactList = contactService.addContact(userDTO, email);

        return new ResponseEntity<>(contactList, HttpStatus.OK);
    }
}