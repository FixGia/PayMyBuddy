package com.project.paymybuddy.Domain.Service;

import com.project.paymybuddy.model.User.UserEntity;

import java.util.List;
import java.util.Optional;

public interface ContactService  {

    List<UserEntity> findEveryContactBelongUser(Long id);

    Optional<UserEntity> findAContactBelongUser (String email, Long id);

    void deleteContact(Long id, String email);

    List<UserEntity> addContact(Long id, String email);

}
