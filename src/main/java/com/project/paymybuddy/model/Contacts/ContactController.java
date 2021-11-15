package com.project.paymybuddy.model.Contacts;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(path ="contacts")
@AllArgsConstructor
@Slf4j
public class ContactController {

    private final ContactService contactService;



    @GetMapping
    public ResponseEntity<List<Contact>> displayedContact(long id){
        List<Contact> contact = contactService.findAllContactsByUsersId(id);
        return  new ResponseEntity<>(contact, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<List<Contact>> addContact(Long id, String email) {

       List<Contact> contact = contactService.addContact(id, email);
        return new ResponseEntity<>(contact, HttpStatus.OK);
    }

    @DeleteMapping("delete")
    public ResponseEntity<Contact> deleteContact(Long id){
        contactService.deleteContact(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}


