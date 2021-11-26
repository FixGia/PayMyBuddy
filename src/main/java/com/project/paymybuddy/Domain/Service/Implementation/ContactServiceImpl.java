package com.project.paymybuddy.Domain.Service.Implementation;

import com.project.paymybuddy.Domain.Service.ContactService;
import com.project.paymybuddy.Exception.DataNotFoundException;
import com.project.paymybuddy.Login.registration.UserDTO;
import com.project.paymybuddy.model.User.UserEntity;
import com.project.paymybuddy.model.User.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ContactServiceImpl implements ContactService {

    UserRepository userRepository;

    /**
     * Users addContact
     * add a Contact in ContactList
     *
     * @param userDTO
     * @param email
     * @return A list with one more user into
     */
    public List<UserEntity> addContact(UserDTO userDTO, String email) {

        Optional<UserEntity> users = userRepository.findUserEntityByEmail(userDTO.getUserName());
        List<UserEntity> contactList = users.get().getContactList();
        Optional<UserEntity> contactToAdd = userRepository.findUserEntityByEmail(email);
        if (contactToAdd.isPresent()) {
            UserEntity contact = contactToAdd.get();
            contactList.add(contact);
            userRepository.save(users.get());
            log.info("add a new contact in ContactList");
        } else {
            log.error("Can't add contact to ContactList");
        }
        return contactList;
    }

    @Override
    public Optional<UserEntity> findAContactBelongUser(UserDTO userDTO, Long id) {
        return Optional.empty();
    }

    /**
     * Users deleteContact
     * delete a contact in user's contactList
     *
     * @param id
     * @param email
     */
    public void deleteContact(Long id, String email) {

        Optional<UserEntity> activeUsers = userRepository.findById(id);
        List<UserEntity> contactList = activeUsers.get().getContactList();

        if (!contactList.isEmpty()) {
            Optional<UserEntity> userToDelete = userRepository.findUserEntityByEmail(email);
            for (UserEntity user : contactList) {
                if (user == userToDelete.get()) {
                    try {
                        userRepository.delete(user);
                        log.info(" Contact has been deleted");
                    } catch (DataNotFoundException exception) {
                        log.error("Cant delete contact because he doesn't exist in DB");
                        exception.printStackTrace();
                    }
                } else log.error(" userToDelete not found");
            }
        } else log.error("contactList is empty");
    }


    /**
     * Users findAllContact
     * used to find All User's Contact
     *
     * @param userDTO
     * @return a list of All user's contact
     */
    public List<UserEntity> findEveryContactBelongUser(UserDTO userDTO) {

        try {
            Optional<UserEntity> activeUser = userRepository.findUserEntityByEmail(userDTO.getUserName());
            List<UserEntity> contactList = activeUser.get().getContactList();
            return contactList;
        } catch (DataNotFoundException exception) {
            exception.printStackTrace();
        }
        log.error("Can't find contactList of user {}", userDTO.getUserName());
        return Collections.emptyList();
    }

    public Optional<UserEntity> findAContactBelongUser (String email, Long id) {

        //TODO Fix This !
        try {
            Optional<UserEntity> users = userRepository.findById(id);
            List<UserEntity> contactList = users.get().getContactList();
                for (UserEntity user : contactList) {
                    Optional<UserEntity> contactToFind = userRepository.findUserEntityByEmail(email);
                    if (user == contactToFind.get()) {
                        log.debug("Contact was found");
                        return Optional.of(contactToFind.get());
                    }
                }

        } catch (DataNotFoundException exception) {
            exception.printStackTrace();
        }
        log.error("Can't find contact");
        return Optional.empty();
    }
}
