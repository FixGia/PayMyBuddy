package com.project.paymybuddy.Login.Email;

import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public interface EmailSender {

    void send(String to, String email);

}
