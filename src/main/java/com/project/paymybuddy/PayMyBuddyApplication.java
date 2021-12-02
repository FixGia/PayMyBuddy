package com.project.paymybuddy;

import com.project.paymybuddy.Domain.DTO.TransactionDTO;
import com.project.paymybuddy.Domain.Service.ExternalTransactionService;
import com.project.paymybuddy.model.User.Role;
import com.project.paymybuddy.model.User.UserEntity;
import com.project.paymybuddy.model.User.UserRepository;
import com.project.paymybuddy.model.User.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Optional;

@SpringBootApplication
@AllArgsConstructor
public class PayMyBuddyApplication {


    public static void main(String[] args) {
        SpringApplication.run(PayMyBuddyApplication.class, args);
    }

    }