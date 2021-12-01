package com.project.paymybuddy.Login.Authentication;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class IndexController {

    @GetMapping(value = "")
    public ResponseEntity<Object> index()  {
        return new ResponseEntity<>("Je suis connecté", HttpStatus.OK);
    }
}