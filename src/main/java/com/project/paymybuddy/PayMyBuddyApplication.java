package com.project.paymybuddy;

import com.project.paymybuddy.Domain.Service.ExternalTransactionService;
import com.project.paymybuddy.DAO.User.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AllArgsConstructor
public class PayMyBuddyApplication {


    UserRepository userRepository;
    ExternalTransactionService externalTransactionService;


    public static void main(String[] args) {
        SpringApplication.run(PayMyBuddyApplication.class, args);
    }


}