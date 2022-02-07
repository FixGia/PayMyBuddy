package com.project.paymybuddy.Entity.User;


import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
    void deleteByName(String name);
}
