package com.project.paymybuddy.DAO.User;

import com.project.paymybuddy.Login.Authentication.JwtProvider;
import com.project.paymybuddy.Login.Authentication.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;


@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private JwtProvider jwtProvider;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, JwtProvider jwtProvider, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;

        this.jwtProvider = jwtProvider;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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

    public UserDTO authenticate(String userName, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
        UserEntity userEntity = this.userRepository.findUserEntityByEmail(userName).orElseThrow(() -> new UsernameNotFoundException("Error authentication! username " + userName + " or password incorrect"));
        String newToken = jwtProvider.createToken(userName, userEntity.getAppUserRole(), new Date());
        UserDTO userDTO = new UserDTO(userEntity.getId(), userEntity.getEmail(), newToken);

        return userDTO;
    }

}
