package com.project.paymybuddy.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Iterable<UserEntity> findAll();

    Optional<UserEntity> updateUsers(Long id, UserEntity userEntity);

    Optional<UserEntity> findUsersById(Long id);

    void  deleteUsersById(Long id);

    Optional<UserEntity> updateUserWallet(Long id, UserEntity userEntity);



}
