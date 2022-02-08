package com.project.paymybuddy.Controller;

import com.project.paymybuddy.Service.ContactService;
import com.project.paymybuddy.Entity.User.UserEntity;
import com.project.paymybuddy.Service.UserService;
import com.project.paymybuddy.Exception.DataNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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

    private final ContactService contactService;
    private final UserService userService;


    @GetMapping(value = {"/Contacts"})
    public String displayedContactList(Model model) {

        try {
            List<UserEntity> contactList = contactService.findEveryContactBelongUser();
            model.addAttribute("contacts", contactList);
            List<UserEntity> everyUsers = userService.getUsers();
            everyUsers.remove(userService.getCurrentUser());
            model.addAttribute("notContacts", everyUsers);
            log.info(" display {}", contactList);
            new ResponseEntity<>(contactList, HttpStatus.OK);
            return "Contacts";
        } catch (DataNotFoundException dataNotFoundException) {
            log.error(" can't display contacts");
            return "/error";
        }
    }


    @GetMapping("/addContact")
    public String addContactToContactList(Model model, @RequestParam String email) {

        try {
            List<UserEntity> contactList = contactService.findEveryContactBelongUser();
            model.addAttribute("contacts", contactList);
            List<UserEntity> everyUsers = userService.getUsers();
            everyUsers.remove(userService.getCurrentUser());
            model.addAttribute("notContacts", everyUsers);
            List<UserEntity> contactListCurrentUser = contactService.addContact(email);
            new ResponseEntity<>(contactListCurrentUser, HttpStatus.OK);
            log.info("Contact {} add with success", email);
            return "Contacts";
        } catch (DataNotFoundException dataNotFoundException) {
            log.error("can't add contact");
            return "/error";
        }
    }

    @GetMapping("/deleteContact")
    public String deleteContactInContactList(@RequestParam String email, Model model) {

        try {

            List<UserEntity> contactList = contactService.findEveryContactBelongUser();
            model.addAttribute("contacts", contactList);
            List<UserEntity> everyUsers = userService.getUsers();
            everyUsers.remove(userService.getCurrentUser());
            model.addAttribute("notContacts", everyUsers);
            contactService.deleteContactInContactList(email);
            log.info("Contact {} was deleted", email);
            return "Contacts";
        } catch (DataNotFoundException dataNotFoundException) {
            log.error("can't delete this contact");
            return "/error";
        }
    }

}