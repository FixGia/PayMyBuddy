package com.project.paymybuddy.Login.Authentication;

import com.project.paymybuddy.DAO.User.UserRepository;
import com.project.paymybuddy.DAO.User.UserService;
import com.project.paymybuddy.Login.registration.SignInRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final UserService userService;

    public AuthController(UserRepository userRepository, JwtProvider jwtProvider, UserService userService) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
        this.userService = userService;
    }


    @PostMapping(value= "/signing")
    public ResponseEntity<Object> signing(@RequestBody @NotNull SignInRequest data, HttpServletRequest httpServletRequest) throws Exception{
        try {
            String username = data.getEmail();
            UserDTO userEntity = userService.authenticate(username, data.getPassword());

            return new ResponseEntity<>(userEntity,OK );

        } catch (AuthenticationException e) {
            return new ResponseEntity<>(e.getMessage(), INTERNAL_SERVER_ERROR);
        }
    }


}