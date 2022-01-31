package com.project.paymybuddy.Controller;

import com.project.paymybuddy.Service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
@AllArgsConstructor
public class IndexController {

    UserService userService;

    @GetMapping(value = {""})
    public String index(Model model) {

        if ( userService.getCurrentUser() != null) {
            model.addAttribute("currentUser", userService.getCurrentUser());
            return "Home";
        }
        return "/Index";
    }


    @GetMapping(value = {"/Home"})
    public String home(Model model) {

        model.addAttribute("currentUser", userService.getCurrentUser());

        return "/Home";
    }


}


