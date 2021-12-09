package com.project.paymybuddy.DAO.User;

import com.project.paymybuddy.DAO.BankAccounts.BankAccountEntity;
import com.project.paymybuddy.DAO.BankAccounts.BankAccountService;
import com.project.paymybuddy.Registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
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

            userRepository.save(currentUserEntity);
            log.info("update Users is a success");
            return Optional.of(currentUserEntity);
        }
        log.error("update Users is a fail");
        return Optional.of(userEntity);
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

     public Optional<UserEntity> updateUserWallet(Long id, UserEntity userEntity) {

       Optional<UserEntity> userToUpdate = userRepository.findById(id);
       if (userToUpdate.isPresent()) {
           userToUpdate.get().setWallet(userEntity.getWallet());
           userRepository.save(userToUpdate.get());
           return userToUpdate;
       }
        log.error("user Not found, wallet can't update");
        return null;
     }

    @Override
    public UserEntity saveUser(@NotNull UserEntity user) {
        log.info("Saving  new User {} to the DB", user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


    @Override
    public Role saveRole(@NotNull Role role) {
        log.info("Saving new role {} to the DB", role.getName());
        return roleRepository.save(role);
    }


    @Override
    public void addRoleToUser(String username, String roleName) {
        UserEntity user = userRepository.findByEmail(username);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);

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
