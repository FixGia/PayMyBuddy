package com.project.paymybuddy;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@AllArgsConstructor
public class PayMyBuddyApplication {



    public static void main(String[] args) {
        SpringApplication.run(PayMyBuddyApplication.class, args);
    }

}

