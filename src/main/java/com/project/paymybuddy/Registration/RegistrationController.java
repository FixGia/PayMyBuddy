package com.project.paymybuddy.Registration;

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
        return registrationService.register(request);
    }

    @GetMapping(path= "confirm")
    public String confirm(@RequestParam("token") String token) {
        log.info("registration is valid");
        return registrationService.confirmToken(token);

    }

}
