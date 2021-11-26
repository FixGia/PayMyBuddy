package com.project.paymybuddy.Domain.Controller;

import com.project.paymybuddy.Domain.Service.ContactService;
import com.project.paymybuddy.Login.registration.RegistrationService;
import com.project.paymybuddy.Login.registration.UserDTO;
import com.project.paymybuddy.model.User.UserEntity;
import com.project.paymybuddy.model.User.UserRepository;
import lombok.AllArgsConstructor;
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
@RequestMapping("api/v1/registration/contacts")

public class ContactController {

    ContactService contactService;
    RegistrationService registrationService;
    UserRepository userRepository;
    DaoAuthenticationProvider daoAuthenticationProvider;
    AuthenticationManagerBuilder authenticationManagerBuilder;




    @GetMapping
    public ResponseEntity<List<UserEntity>> displayedContactList(@NotNull UserDTO userDTO) throws Exception {


        List<UserEntity> contactList = contactService.findEveryContactBelongUser(userDTO);
            return new ResponseEntity<>(contactList, HttpStatus.CREATED);

    }

    @PostMapping
    public ResponseEntity<List<UserEntity>> addContactToContactList(UserDTO userDTO, String email){

        List<UserEntity> contactList = contactService.addContact(userDTO, email);

        return new ResponseEntity<>(contactList, HttpStatus.OK);
    }
}