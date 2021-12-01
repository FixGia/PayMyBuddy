package com.project.paymybuddy.DAO.User;

import com.project.paymybuddy.Login.Authentication.UserDTO;

import java.util.Optional;

public interface UserService {

    Iterable<UserEntity> findAll();

    Optional<UserEntity> updateUsers(Long id, UserEntity userEntity);

    Optional<UserEntity> findUsersById(Long id);

    void  deleteUsersById(Long id);

    Optional<UserEntity> updateUserWallet(Long id, UserEntity userEntity);

    UserDTO authenticate(String userName, String password);


}
