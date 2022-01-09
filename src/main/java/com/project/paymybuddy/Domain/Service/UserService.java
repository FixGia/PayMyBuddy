package com.project.paymybuddy.Domain.Service;

import com.project.paymybuddy.DAO.User.Role;
import com.project.paymybuddy.DAO.User.UserEntity;
import com.project.paymybuddy.Domain.DTO.UserRequest;

import java.util.List;
import java.util.Optional;

public interface UserService {


    Optional<UserEntity> updateUsers(UserEntity user);

    Optional<UserEntity> findUsersById(Long id);

    void deleteUserByEmail(String email);

    Optional<UserEntity> updateUserWallet(Long id, UserEntity userEntity);

    UserEntity saveUser(UserEntity user);

    Role saveRole(Role role);

    UserEntity getCurrentUser();

    void addRoleToUser(String username, String roleName);

    UserEntity getUser(String username);

    List<UserEntity> getUsers();

    UserEntity updateProfile(UserRequest userRequest);
}
