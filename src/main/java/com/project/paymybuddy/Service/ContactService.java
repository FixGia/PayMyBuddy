package com.project.paymybuddy.Service;

import com.project.paymybuddy.Entity.User.UserEntity;

import java.util.List;
import java.util.Optional;

public interface ContactService  {

    List<UserEntity> findEveryContactBelongUser();

    Optional<UserEntity> findAContactBelongUser (String email);

    void deleteContactInContactList(String email);

    List<UserEntity> addContact(String email);


}
