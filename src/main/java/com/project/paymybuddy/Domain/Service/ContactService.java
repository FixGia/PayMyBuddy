package com.project.paymybuddy.Domain.Service;

import com.project.paymybuddy.Login.Authentication.UserDTO;
import com.project.paymybuddy.DAO.User.UserEntity;

import java.util.List;
import java.util.Optional;

public interface ContactService  {

    List<UserEntity> findEveryContactBelongUser(UserDTO userDTO);

    Optional<UserEntity> findAContactBelongUser (UserDTO userDTO, Long id);

    void deleteContact(Long id, String email);

    List<UserEntity> addContact(UserDTO userDTO, String email);


}
