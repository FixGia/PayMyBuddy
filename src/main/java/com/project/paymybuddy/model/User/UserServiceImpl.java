package com.project.paymybuddy.model.User;

import com.project.paymybuddy.Login.token.ConfirmationToken;
import com.project.paymybuddy.Login.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;
    private final static String USER_NOT_FOUND_MSG = "user with email %s not found";
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;


    /**
     * User loadUserByUsername
     *
     * @param email
     * @return user find by email
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return userRepository.findUserEntityByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }


    /**
     * User signUpUser
     * <p>
     * use to signUp new user and deliver a confirmation token
     *
     * @param userEntity
     * @return new token
     */
    public String signUpUser(@NotNull UserEntity userEntity) {
        boolean userExists =
                userRepository.findUserEntityByEmail(userEntity.getEmail()).isPresent();
        if (userExists) {
            throw new IllegalStateException("email not valid");
        }
        String encodedPassword = bCryptPasswordEncoder.encode(userEntity.getPassword());

        userEntity.setPassword(encodedPassword);
        userRepository.save(userEntity);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                userEntity);
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        return token;
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
     * findAll
     *
     * @return All users
     */
    @Override
    public Iterable<UserEntity> findAll() {
        Iterable<UserEntity> users = userRepository.findAll();
        return users;

    }

    /**
     * Users update Users
     *
     * @param id
     * @param userEntity
     * @return An update User
     */
    @Override
    public Optional<UserEntity> updateUsers(Long id, UserEntity userEntity) {

        Optional<UserEntity> usersToUpdate = userRepository.findById(id);
        if (usersToUpdate.isPresent()) {
            UserEntity currentUserEntity = usersToUpdate.get();

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


    /**
     * Users findUsersById
     *
     * @param id
     * @return a User
     */
    @Override
    public Optional<UserEntity> findUsersById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Users deleteUser
     * use to delete a user by Id
     *
     * @param id
     */
    @Override
    public void deleteUsersById(Long id) {
        userRepository.deleteById(id);
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

}
