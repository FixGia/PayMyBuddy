package com.project.paymybuddy.model.Contacts;

import com.project.paymybuddy.Exception.DataNotFoundException;
import com.project.paymybuddy.model.User.UserRepository;
import com.project.paymybuddy.model.User.Users;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
@Slf4j
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final UserRepository userRepository;

    public List<Contact> findAllContactsByUsersId(Long id) {

        try {
          Optional<Users> user = userRepository.findById(id);
          List<Contact> contactList = user.get().getContactList();
          log.info("all contacts are displayed");
            return contactList;
        } catch (DataNotFoundException dataNotFoundException) {
            dataNotFoundException.printStackTrace();
        }
        log.error("fail to display contacts");
        return null;
    }


    public List<Contact> addContact(Long id, String email) {

       Optional<Users> users = userRepository.findById(id);
       List<Contact> contactList = users.get().getContactList();
       Optional<Contact> contactToAdd = contactRepository.findContactByUsers_Email(email);
       if (contactToAdd.isPresent()) {
           Contact newContact = contactToAdd.get();
           contactList.add(newContact);
           contactRepository.save(newContact);
           userRepository.save(users.get());
                log.info("add a new contact in ContactList");
       }
            else {
                log.error("Can't add contact to ContactList");
       }
        return contactList;
    }

        public void deleteContact(Long id) {

        Optional<Contact> contactToDelete= contactRepository.findById(id);
        if (contactToDelete.isPresent()){
            contactRepository.deleteById(id);
            log.info(" Contact has been deleted");
        }
        log.error("Cant delete contact because he doesn't exist in DB");

        }

        }

