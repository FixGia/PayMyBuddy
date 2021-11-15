package com.project.paymybuddy.model.User;

import com.project.paymybuddy.Login.token.ConfirmationToken;
import com.project.paymybuddy.Login.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class UserServiceImpl implements UserDetailsService,UserService {

    private final UserRepository userRepository;
    private final static String USER_NOT_FOUND_MSG = "user with email %s not found";
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;


    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }


    public String signUpUser(Users users){
        boolean userExists =
                userRepository.findByEmail(users.getEmail()).isPresent();
        if(userExists)  {
            throw  new IllegalStateException("email not valid");
        }
        String encodedPassword = bCryptPasswordEncoder.encode(users.getPassword());

        users.setPassword(encodedPassword);
        userRepository.save(users);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                users);
        confirmationTokenService.saveConfirmationToken(confirmationToken);


        return token;
    }

    public int enableAppUser(String email) {
        return userRepository.enableAppUser(email);
    }


    @Override
    public Iterable<Users> findAll() {
        Iterable<Users> users = userRepository.findAll();
        return users;

    }

    @Override
    public Optional<Users> updateUsers(Long id, Users users) {

        Optional<Users> usersToUpdate = userRepository.findById(id);
        if (usersToUpdate.isPresent()) {
            Users currentUsers = usersToUpdate.get();

            String firstname = users.getFirstname();
            if (firstname != null) {
                currentUsers.setFirstname(firstname);
            }
            String lastname = users.getLastname();
            if (lastname != null) {
                currentUsers.setLastname(lastname);
            }
            String email = users.getEmail();
            if (email != null) {
                currentUsers.setEmail(email);
            }
            String civility = users.getCivility();
            if (civility !=null) {
                currentUsers.setCivility(civility);
            }

            userRepository.save(currentUsers);
            log.info("update Users is a success");
            return Optional.of(currentUsers);
        }
        log.error("update Users is a fail");
        return Optional.of(users);
    }


    @Override
    public Optional<Users> findUsersById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void deleteUsersById(Long id) {
        userRepository.deleteById(id);
    }
}
