package com.project.paymybuddy.Domain.Controller;

import com.project.paymybuddy.Domain.DTO.RegistrationRequest;
import com.project.paymybuddy.Domain.Service.RegistrationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@Slf4j
public class RegistrationController {

    private RegistrationService registrationService;

    @GetMapping( value = {"/checkout" } )
    public String displaySignUpPerform(){
        return "/checkout";
    }


    @PostMapping( value = {"/checkout"} )
    public String register(@ModelAttribute RegistrationRequest request) {

        try {
            registrationService.register(request);
            log.info("registration is valid");
            return "/Index";

        } catch (Exception exception) {
            exception.printStackTrace();
            log.error("registration fail");
            return "/Error";
        }

    }
}
