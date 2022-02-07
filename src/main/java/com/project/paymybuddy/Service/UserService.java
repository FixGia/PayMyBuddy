package com.project.paymybuddy.Service;

import com.project.paymybuddy.Entity.User.Role;
import com.project.paymybuddy.Entity.User.UserEntity;
import com.project.paymybuddy.DTO.UserRequest;

import java.util.List;
import java.util.Optional;

public interface UserService {


    Optional<UserEntity> updateUsers(UserEntity user);

    Optional<UserEntity> findUsersById(Long id);

    void deleteUserByEmail(String email);

    UserEntity updateUserWallet(UserEntity userEntity);

    UserEntity saveUser(UserEntity user);

    Role saveRole(Role role);

    UserEntity getCurrentUser();

    void addRoleToUser(String username, String roleName);

    UserEntity getUser(String username);

    List<UserEntity> getUsers();

    UserEntity updateProfile(UserRequest userRequest);
}
