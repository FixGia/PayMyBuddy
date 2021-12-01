package com.project.paymybuddy.Login.Authentication;

import com.project.paymybuddy.DAO.User.AppUserRole;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;


@Data
@Component
@ConfigurationProperties(prefix = "security")
@Validated
public class SecurityPropertiesExtension {

    private AppUserRole appUserRole;
    private Jwt jwt = new Jwt();




    @Getter
    @Setter
    public static class Jwt {
        private String header;
        private String headerPrefix;
        private String secret;
        private Long expiration;
    }

}

