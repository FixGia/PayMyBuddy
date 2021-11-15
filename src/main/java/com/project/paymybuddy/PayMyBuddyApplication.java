package com.project.paymybuddy;

import com.project.paymybuddy.model.Contacts.Contact;
import com.project.paymybuddy.model.Contacts.ContactRepository;
import com.project.paymybuddy.model.User.UserRepository;
import com.project.paymybuddy.model.User.UserServiceImpl;
import com.project.paymybuddy.model.User.Users;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@AllArgsConstructor
public class PayMyBuddyApplication implements CommandLineRunner {


    UserRepository userRepository;
    ContactRepository contactRepository;


    public static void main(String[] args) {
        SpringApplication.run(PayMyBuddyApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        Users users = new Users();
        Contact contact= new Contact();
        contact.setFirstname("jacques");
        List<Contact> contactList = new ArrayList<>();
        contactList.add(contact);
        users.setContactList(contactList);
        userRepository.save(users);
        contactRepository.save(contact);

    }
}