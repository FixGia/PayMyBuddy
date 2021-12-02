package com.project.paymybuddy.Login.security;
import com.project.paymybuddy.model.User.UserEntity;
import com.project.paymybuddy.model.User.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class TestController {

    private final UserService userService;


    @GetMapping("/test")
    public String testController() {

       UserEntity currentUser =  userService.getCurrentUser();
       log.info("L'user : {} est connecté ", currentUser.getEmail());
        return " Tu es connecté {}";
    }
}
