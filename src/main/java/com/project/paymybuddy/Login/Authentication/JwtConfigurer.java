package com.project.paymybuddy.Login.Authentication;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

JwtTokenFilter jwtTokenFilter;

public JwtConfigurer(JwtTokenFilter jwtTokenFilter) {
    this.jwtTokenFilter=jwtTokenFilter;
}

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        SecurityFilterExceptionHandler securityFilterExceptionHandler = new SecurityFilterExceptionHandler();

        builder
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(securityFilterExceptionHandler, JwtTokenFilter.class);

}
}
