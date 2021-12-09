package com.project.paymybuddy.DAO.User;

import java.util.List;
import java.util.Optional;

public interface UserService {


    Optional<UserEntity> updateUsers(UserEntity userEntity);

    Optional<UserEntity> findUsersById(Long id);

    void deleteUserByEmail(String email);

    Optional<UserEntity> updateUserWallet(Long id, UserEntity userEntity);

    UserEntity saveUser(UserEntity user);

    Role saveRole(Role role);

    UserEntity getCurrentUser();

    void addRoleToUser(String username, String roleName);

    UserEntity getUser(String username);

    List<UserEntity> getUsers();

}
