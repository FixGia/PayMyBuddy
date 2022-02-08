package com.project.paymybuddy.Service.Implementation;

import com.project.paymybuddy.Entity.Role.Role;
import com.project.paymybuddy.Entity.Role.RoleRepository;
import com.project.paymybuddy.Entity.User.UserEntity;
import com.project.paymybuddy.Entity.User.UserRepository;
import com.project.paymybuddy.DTO.UserRequest;
import com.project.paymybuddy.Service.UserService;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity user = userRepository.findByEmail(username);
        if (user == null) {
            log.error("User not Found in the DB");
            throw new UsernameNotFoundException("User not Found in Db");
        } else {
            log.info("User found in the Db {}", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }


    /**
     * enableAppUser
     * use to enable a User
     *
     * @param email
     * @return enable user
     */
    public int enableAppUser(String email) {
        return userRepository.enableAppUser(email);
    }



    /**
     * Users update Users
     *
     * @param userEntity
     * @return An update User
     */
    @Override
    public Optional<UserEntity> updateUsers(UserEntity userEntity) {

        UserEntity usersToUpdate = userRepository.findByEmail(userEntity.getEmail());
        if (!(usersToUpdate == null)) {
            UserEntity currentUserEntity = usersToUpdate;

            String firstname = userEntity.getFirstname();
            if (firstname != null) {
                currentUserEntity.setFirstname(firstname);
            }
            String lastname = userEntity.getLastname();
            if (lastname != null) {
                currentUserEntity.setLastname(lastname);
            }
            String email = userEntity.getEmail();
            if (email != null) {
                currentUserEntity.setEmail(email);
            }
            String civility = userEntity.getCivility();
            if (civility != null) {
                currentUserEntity.setCivility(civility);
            }
            List<UserEntity> contactList = userEntity.getContactList();
            if(contactList != null) {
                currentUserEntity.setContactList(contactList);
            }


            userRepository.save(currentUserEntity);
            log.info("update Users is a success");
            return Optional.of(currentUserEntity);
        }
        log.error("update Users is a fail");
        return Optional.of(userEntity);
    }

    public UserEntity updateProfile(UserRequest userRequest) {
        UserEntity userToUpdate = getCurrentUser();
        if (userToUpdate != null) {

            String firstname = userRequest.getFirstname();
            if (firstname != null) {
                userToUpdate.setFirstname(firstname);
            }
            String lastname = userRequest.getLastname();
            if (lastname != null) {
                userToUpdate.setLastname(lastname);
            }
            String email = userRequest.getEmail();
            if (email != null) {
                userToUpdate.setEmail(email);
            }
            String password = userRequest.getPassword();
            if (password != null) {
                userToUpdate.setPassword(passwordEncoder.encode(password));
            }
            userRepository.save(userToUpdate);
            log.info("update Users is a success");
            return userToUpdate;
        }
        log.error("update Users is a fail");
        return null;
    }



    @Override
    public Optional<UserEntity> findUsersById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Users deleteUserByEmail
     * use to delete a user by Email
     *
     * @param email
     */
    @Override
    public void deleteUserByEmail(String email) {
       userRepository.deleteByEmail(email);
    }

    public UserEntity updateUserWallet(UserEntity userEntity) {

       UserEntity userToUpdate = userRepository.findByEmail(userEntity.getEmail());
       if (userToUpdate != null ) {
           userToUpdate.setWallet(userEntity.getWallet());
           userRepository.save(userToUpdate);
           return userToUpdate;
       }
        log.error("user Not found, wallet can't update");
        return null;
     }

    @Override
    public UserEntity saveUser(@NotNull UserEntity user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        log.info("Saving  new User {} to the DB", user.getEmail());
        return userRepository.save(user);
    }


    @Override
    public Role saveRole(@NotNull Role role) {
        log.info("Saving new role {} to the DB", role.getName());
        return roleRepository.save(role);
    }


    @Override
    public void addRoleToUser(String username, String roleName) {
        try {
            UserEntity user = userRepository.findByEmail(username);
            Role role = roleRepository.findByName(roleName);
            user.getRoles().add(role);
            userRepository.save(user);
            log.info("success to add {} to {}", roleName, username);

        } catch (IllegalStateException illegalStateException) {
            log.error("fail to add{}, to {}", roleName, username);
        }
    }
    @Override
    public UserEntity getUser(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }

    public UserEntity getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return userRepository.findByEmail(username);

    }
}
