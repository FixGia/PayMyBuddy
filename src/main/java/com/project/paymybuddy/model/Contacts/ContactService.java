package com.project.paymybuddy.model.Contacts;

import java.util.List;
import java.util.Optional;


public interface ContactService {


    List<Contact> findAllContactsByUsersId(Long id);

    List<Contact> addContact(Long id,String email);

    void deleteContact(Long id);

}
