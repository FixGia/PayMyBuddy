package com.project.paymybuddy.Domain.Controller;

import com.project.paymybuddy.DAO.User.UserEntity;
import com.project.paymybuddy.Domain.DTO.UserRequest;
import com.project.paymybuddy.Domain.Service.UserService;
import com.project.paymybuddy.Exception.DataNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Slf4j
public class ProfileController {

    private UserService userService;

    @GetMapping("api/user/profile/display")
    public ResponseEntity<UserEntity> GetProfile() {

        try {
            UserEntity currentUser = userService.getCurrentUser();
            log.info("Profile found");
            return ResponseEntity.ok(currentUser);

        } catch (DataNotFoundException exception) {
            exception.printStackTrace();
            log.error("Profile Not Found");
            return (ResponseEntity<UserEntity>) ResponseEntity.notFound();
        }
    }

    @PostMapping("api/user/profile/update")
    public ResponseEntity<UserEntity> UpdateProfile(@RequestBody UserRequest userRequest) {

        UserEntity user = userService.updateProfile(userRequest);
        return ResponseEntity.ok(user);
    }

}

