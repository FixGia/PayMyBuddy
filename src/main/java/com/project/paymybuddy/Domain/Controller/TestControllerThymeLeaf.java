package com.project.paymybuddy.Domain.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/testthymeleaf")
public class TestControllerThymeLeaf {

    @GetMapping
    public String test(){

        return "test";
    }
}
