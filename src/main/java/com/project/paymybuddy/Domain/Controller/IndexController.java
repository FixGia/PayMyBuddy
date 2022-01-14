package com.project.paymybuddy.Domain.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class IndexController {


    @GetMapping(value = {"/Index"})
    public String index() {
        return "/Index";
    }

    @GetMapping(value = "/Home")
    public String home() {
        return "/Home";
    }


}


