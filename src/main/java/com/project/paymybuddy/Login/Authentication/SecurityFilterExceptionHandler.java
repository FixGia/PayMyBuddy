package com.project.paymybuddy.Login.Authentication;

import com.project.paymybuddy.Exception.InvalidJwtAuthenticationException;
import com.sun.istack.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Slf4j
@Component
public class SecurityFilterExceptionHandler extends OncePerRequestFilter implements AuthenticationEntryPoint {

    @Override
    public void doFilterInternal(HttpServletRequest httpServletRequest, @NotNull HttpServletResponse httpServletResponse, FilterChain filterChain) throws IOException {
        try {
            log.debug("SecurityFilterExceptionHandler "+httpServletRequest.getRequestURI()+" URI is executed" );
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } catch (AccessDeniedException | InvalidJwtAuthenticationException e) {
            log.error(e.getMessage()+" "+e.getLocalizedMessage());
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            httpServletResponse.getWriter().write((e.getMessage()));
        } catch (Exception e){
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            httpServletResponse.getWriter().write((e.getMessage()));
        }
    }

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException authException) throws IOException {
        new ResponseEntity<Object>(authException.getMessage(), UNAUTHORIZED);
    }



}
