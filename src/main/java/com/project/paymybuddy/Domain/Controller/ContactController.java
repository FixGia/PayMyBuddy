package com.project.paymybuddy.Domain.Controller;

import com.project.paymybuddy.Domain.Service.ContactService;
import com.project.paymybuddy.model.User.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/contacts")
public class ContactController {

    ContactService contactService;


    @GetMapping
    public ResponseEntity<List<UserEntity>> displayedContactList(@RequestParam(name="user_id") Long id) {

        List<UserEntity> contactList = contactService.findEveryContactBelongUser(id);

        return new ResponseEntity<>(contactList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<List<UserEntity>> addContactToContactList(@RequestParam(name="user_id")Long id, @RequestBody String email){

        List<UserEntity> contactList = contactService.addContact(id, email);

        return new ResponseEntity<>(contactList, HttpStatus.OK);
    }
}