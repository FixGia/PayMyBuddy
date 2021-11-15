package com.project.paymybuddy.model.User;

import java.util.Optional;

public interface UserService {

    Iterable<Users> findAll();

    Optional<Users> updateUsers(Long id, Users users);

    Optional<Users> findUsersById(Long id);

    void  deleteUsersById(Long id);

}
