package com.project.paymybuddy.Domain.Controller;

import com.project.paymybuddy.Domain.Service.Implementation.ContactServiceImpl;
import com.project.paymybuddy.DAO.User.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")

public class ContactController {

    private ContactServiceImpl contactService;




    @GetMapping("/contacts")
    public ResponseEntity<List<UserEntity>> displayedContactList() throws Exception {


        List<UserEntity> contactList = contactService.findEveryContactBelongUser();
            return new ResponseEntity<>(contactList, HttpStatus.OK);
    }

    @GetMapping("/contact")
    public ResponseEntity<Optional<UserEntity>> displayedContact(@RequestParam String email){
        Optional<UserEntity> userEntity = contactService.findAContactBelongUser(email);
        return  new ResponseEntity<>(userEntity, HttpStatus.OK);
    }

    @PostMapping("/contact")
    public ResponseEntity<List<UserEntity>> addContactToContactList(@RequestParam String email) {

        List<UserEntity> contactList = contactService.addContact(email);

        return new ResponseEntity<>(contactList, HttpStatus.OK);
    }

    @DeleteMapping("/contact")
    public ResponseEntity<List<UserEntity>> deleteContactInContactList(@RequestParam String email) {

        contactService.deleteContactInContactList(email);
        return ResponseEntity.ok(contactService.findEveryContactBelongUser());

    }
}