package com.project.paymybuddy.Domain.Controller;

import com.project.paymybuddy.Domain.Service.Implementation.ContactServiceImpl;
import com.project.paymybuddy.Registration.RegistrationService;
import com.project.paymybuddy.DAO.User.UserEntity;
import com.project.paymybuddy.DAO.User.UserRepository;
import com.project.paymybuddy.DAO.User.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.condition.HeadersRequestCondition;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")

public class ContactController {

    ContactServiceImpl contactService;




    @GetMapping("/contacts")
    public ResponseEntity<List<UserEntity>> displayedContactList(@RequestHeader ("Authorization") String token) throws Exception {


        List<UserEntity> contactList = contactService.findEveryContactBelongUser();
            return new ResponseEntity<>(contactList, HttpStatus.OK);
    }

    @GetMapping("/contact")
    public ResponseEntity<Optional<UserEntity>> displayedContact(@RequestParam String email){
        Optional<UserEntity> userEntity = contactService.findAContactBelongUser(email);
        return  new ResponseEntity<>(userEntity, HttpStatus.OK);
    }

    @PostMapping("/contacts")
    public ResponseEntity<List<UserEntity>> addContactToContactList(@RequestParam String email){

        List<UserEntity> contactList = contactService.addContact(email);

        return new ResponseEntity<>(contactList, HttpStatus.OK);
    }

    @DeleteMapping("/contacts")
    public ResponseEntity<List<UserEntity>> deleteContactInContactList(@RequestParam String email) {

        contactService.deleteContactInContactList(email);

        return ResponseEntity.ok(contactService.findEveryContactBelongUser());
    }
}