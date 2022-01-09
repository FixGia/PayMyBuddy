package com.project.paymybuddy.Domain.Service;

import com.project.paymybuddy.DAO.User.UserEntity;
import com.project.paymybuddy.Domain.DTO.RegistrationRequest;
import com.sun.istack.NotNull;

public interface RegistrationService {

    String signUpUser(@NotNull UserEntity userEntity);

    String register(@NotNull RegistrationRequest request);
}
