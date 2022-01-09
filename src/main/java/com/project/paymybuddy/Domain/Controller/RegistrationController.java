package com.project.paymybuddy.Domain.Controller;

import com.project.paymybuddy.Domain.DTO.RegistrationRequest;
import com.project.paymybuddy.Domain.Service.RegistrationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path ="/api/registration")
@AllArgsConstructor
@Slf4j
public class RegistrationController {

    private RegistrationService registrationService;

    @PostMapping
    public String register(@RequestBody RegistrationRequest request) {
        log.info("registration is valid");
        return registrationService.register(request);

    }


}
