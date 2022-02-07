package com.project.paymybuddy.Service;

import com.project.paymybuddy.Entity.User.UserEntity;
import com.project.paymybuddy.DTO.RegistrationRequest;
import com.sun.istack.NotNull;

public interface RegistrationService {

    String signUpUser(@NotNull UserEntity userEntity);

    String register(@NotNull RegistrationRequest request);
}
