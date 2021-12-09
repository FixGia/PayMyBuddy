package com.project.paymybuddy.Domain.Service.Implementation;

import com.project.paymybuddy.Domain.Service.ContactService;
import com.project.paymybuddy.Exception.DataNotFoundException;
import com.project.paymybuddy.Registration.UserDTO;
import com.project.paymybuddy.DAO.User.UserEntity;
import com.project.paymybuddy.DAO.User.UserRepository;
import com.project.paymybuddy.DAO.User.UserService;
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
    UserService userService;

    /**
     * Users addContact
     * add a Contact in ContactList
     *
     * @return A list with one more user into
     */
    public List<UserEntity> addContact(String email) {

        UserEntity currentUser = userService.getCurrentUser();

        try {
            List<UserEntity> contactList = currentUser.getContactList();
            UserEntity contactToAdd = userService.getUser(email);
            if (contactToAdd != null) {
                contactList.add(contactToAdd);
                userRepository.save(currentUser);
                log.info("add a new contact in ContactList");
                return contactList;
            }

        } catch (DataNotFoundException exception) {

            log.error("ContactList of {} not found", currentUser);
            exception.printStackTrace();
        }
        log.error("Can't add a new contact in ContactList");
        return Collections.emptyList();
    }


    @Override
    public Optional<UserEntity> findAContactBelongUser(String email) {

        UserEntity currentUser = userService.getCurrentUser();
        List<UserEntity> contactList = currentUser.getContactList();
        UserEntity userToFind = userService.getUser(email);

        if (contactList.contains(userToFind)) {
            log.info("Contact has been found");
            return Optional.of(userToFind);
        }
        throw new DataNotFoundException ("Contact isn't present");

    }




    /**
     * Users deleteContact
     * delete a contact in user's contactList
     *
     * @param email
     */
    public void deleteContactInContactList(String email) {

        UserEntity currentUser = userService.getCurrentUser();
        List<UserEntity> contactList = currentUser.getContactList();
        UserEntity userToDelete = userService.getUser(email);

        if (userToDelete != null) {
            if (contactList.contains(userToDelete))
                    try {
                        contactList.remove(userToDelete);
                        userService.saveUser(currentUser);
                        log.info(" Contact has been deleted");
                    } catch (DataNotFoundException exception) {
                        log.error("Cant delete contact because he doesn't exist in DB");
                        exception.printStackTrace();
                    }
                } else log.error(" userToDelete not found");
            }


    /**
     * Users findAllContact
     * used to find All User's Contact
     *
     * @return a list of All user's contact
     */
    public List<UserEntity> findEveryContactBelongUser() {

        UserEntity currentUser = userService.getCurrentUser();

        try {

            List<UserEntity> contactList = currentUser.getContactList();
            log.info("Find ContactList of user {}", currentUser.getEmail());
            return contactList;
        } catch (DataNotFoundException exception) {
            exception.printStackTrace();

        }
        log.error("Can't find contactList of user {}", currentUser.getEmail());
        return Collections.emptyList();
    }

    public Optional<UserEntity> findAContactBelongUser (String email, Long id) {

        //TODO Fix This !
        try {
            Optional<UserEntity> users = userRepository.findById(id);
            List<UserEntity> contactList = users.get().getContactList();
                for (UserEntity user : contactList) {
                    Optional<UserEntity> contactToFind = Optional.ofNullable(userRepository.findByEmail(email));
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
