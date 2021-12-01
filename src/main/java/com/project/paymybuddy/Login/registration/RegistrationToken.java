package com.project.paymybuddy.Login.registration;

import com.project.paymybuddy.DAO.User.UserEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class RegistrationToken {

    String token;
    LocalDateTime createdAt;
    LocalDateTime expiredAt;
    UserEntity userEntity;

    public RegistrationToken(String token, LocalDateTime now, LocalDateTime plusMinutes, UserEntity userEntity) {
        this.token = token;
        this.createdAt = now;
        this.expiredAt = plusMinutes;
        this.userEntity = userEntity;
    }
}
