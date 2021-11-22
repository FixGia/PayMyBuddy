package com.project.paymybuddy;

import com.project.paymybuddy.Domain.DTO.TransactionDTO;
import com.project.paymybuddy.Domain.Service.ExternalTransactionService;
import com.project.paymybuddy.model.User.UserEntity;
import com.project.paymybuddy.model.User.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;

@SpringBootApplication
@AllArgsConstructor
public class PayMyBuddyApplication implements CommandLineRunner {


    UserRepository userRepository;
    ExternalTransactionService externalTransactionService;



    public static void main(String[] args) {
        SpringApplication.run(PayMyBuddyApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {

    }
}