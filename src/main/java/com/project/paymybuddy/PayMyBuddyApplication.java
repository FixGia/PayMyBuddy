package com.project.paymybuddy;

import com.project.paymybuddy.DAO.User.Role;
import com.project.paymybuddy.DAO.User.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@AllArgsConstructor
@EnableSwagger2
public class PayMyBuddyApplication implements CommandLineRunner {



    public static void main(String[] args) {
        SpringApplication.run(PayMyBuddyApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {

    }
}

