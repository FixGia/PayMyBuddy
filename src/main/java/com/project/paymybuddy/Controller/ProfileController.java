package com.project.paymybuddy.Controller;

import com.project.paymybuddy.Entity.User.UserEntity;
import com.project.paymybuddy.DTO.UserRequest;
import com.project.paymybuddy.Service.UserService;
import com.project.paymybuddy.Exception.DataNotFoundException;
import com.project.paymybuddy.Exception.NotConformDataException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@AllArgsConstructor
@Slf4j
public class ProfileController {

    private final UserService userService;

    @GetMapping(value = {"/Profile"} )
    public String GetProfile(Model model) {
        try {

            UserEntity currentUser = userService.getCurrentUser();
            model.addAttribute(currentUser);
            log.info("Profile found {} ", currentUser.getEmail());
            ResponseEntity.ok(currentUser);
            return "Profile";

        } catch (DataNotFoundException exception) {
            exception.printStackTrace();
            log.error("Profile Not Found");
            return "/error";
        }
    }

    @GetMapping(value = {"/UpdateProfile"})
    public String updateProfile(){
        return "/UpdateProfile";
    }


    @PostMapping(value = {"/Profile/update"} )
    public String UpdateProfile(@ModelAttribute UserRequest userRequest, Model model) {

        try {
            UserEntity user = userService.updateProfile(userRequest);
            ResponseEntity.ok(user);
            model.addAttribute(user);
            log.info(" Request to update {} profile is a success", userRequest.getFirstname()+" "+userRequest.getLastname());
            return "/Profile";
        } catch (NotConformDataException exception) {
            exception.printStackTrace();
            log.error(" Request to update {} profile failed", userService.getCurrentUser());
            return "/error";
        }
    }
}

