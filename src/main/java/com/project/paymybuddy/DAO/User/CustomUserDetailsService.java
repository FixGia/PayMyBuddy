package com.project.paymybuddy.DAO.User;

import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Data
@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository users;
    private final static String USER_NOT_FOUND_MSG = "user with email %s not found";
    public CustomUserDetailsService(UserRepository users) {
        this.users = users;
    }

    /**
     * User loadUserByUsername
     *
     * @param email
     * @return user find by email
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return users.findUserEntityByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

}
