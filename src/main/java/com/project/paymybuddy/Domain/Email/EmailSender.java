package com.project.paymybuddy.Domain.Email;

import org.springframework.context.annotation.ComponentScan;

//@ComponentScan
public interface EmailSender {

    void send(String to, String email);

}
