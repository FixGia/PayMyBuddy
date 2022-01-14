package com.project.paymybuddy.Domain.Controller;

import com.project.paymybuddy.Domain.Service.ContactService;
import com.project.paymybuddy.Domain.Service.Implementation.ContactServiceImpl;
import com.project.paymybuddy.DAO.User.UserEntity;
import com.project.paymybuddy.Domain.Service.UserService;
import com.project.paymybuddy.Exception.DataNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.rule.Mode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@Slf4j
public class ContactController {

    private ContactService contactService;
    private UserService userService;


    @GetMapping(value = {"/Contacts"} )
    public String displayedContactList(Model model) {

        try {
            List<UserEntity> contactList = contactService.findEveryContactBelongUser();
            model.addAttribute("contacts", contactList);
            List<UserEntity> everyUsers = userService.getUsers();
            model.addAttribute("notContacts", everyUsers);
            log.info(" display {}", contactList);
            new ResponseEntity<>(contactList, HttpStatus.OK);
            return "/Contacts";
        } catch (DataNotFoundException dataNotFoundException) {
            log.error(" can't display contacts");
            return "/Error";
        }
    }


    @GetMapping("/addContact")
    public String addContactToContactList(@RequestParam String email) {

        List<UserEntity> contactList = contactService.addContact(email);
        new ResponseEntity<>(contactList, HttpStatus.OK);
        return"Contacts";
    }

    @GetMapping("/deleteContact")
    public String deleteContactInContactList(@RequestParam String email) {
        contactService.deleteContactInContactList(email);
        return "/Contacts";

    }
}