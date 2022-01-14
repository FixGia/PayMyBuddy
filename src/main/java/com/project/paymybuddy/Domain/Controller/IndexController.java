package com.project.paymybuddy.Domain.Controller;

import com.project.paymybuddy.Domain.Service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
@AllArgsConstructor
public class IndexController {

    UserService userService;

    @GetMapping(value = {"/Index"})
    public String index() {
        return "/Index";
    }

    @GetMapping(value = {"/Home"})
    public String home(Model model) {


        model.addAttribute("currentUser", userService.getCurrentUser());

        return "/Home";
    }


}


