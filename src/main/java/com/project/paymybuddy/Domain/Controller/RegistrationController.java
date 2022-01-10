package com.project.paymybuddy.Domain.Controller;

import com.project.paymybuddy.Domain.DTO.RegistrationRequest;
import com.project.paymybuddy.Domain.Service.RegistrationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
//@RequestMapping
@AllArgsConstructor
@Slf4j
public class RegistrationController {

    private RegistrationService registrationService;

    @GetMapping( value = "/checkout")
    public String displaySignUpPerform(){
        return "checkout";
    }

    @PostMapping(value = {"api/registration"})
    public String register(@RequestBody RegistrationRequest request) {

        try {
            log.info("registration is valid");
            return registrationService.register(request);
        } catch (Exception exception) {
            exception.printStackTrace();
            log.error("registration fail");
            return null;
        }

    }
}
