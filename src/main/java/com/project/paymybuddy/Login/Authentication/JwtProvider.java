package com.project.paymybuddy.Login.Authentication;

import com.project.paymybuddy.DAO.User.AppUserRole;
import com.project.paymybuddy.Exception.InvalidJwtAuthenticationException;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Component
@Slf4j
public class JwtProvider {


    private final SecurityPropertiesExtension spe;
    private final UserDetailsService userDetailsService;

    public JwtProvider(SecurityPropertiesExtension spe, UserDetailsService userService) {
        this.spe = spe;
        this.userDetailsService = userService;
    }

    /**
     * create Token Method
     *
     * @param username
     * @param appUserRole
     * @param date
     * @return
     */
    public String createToken(String username, AppUserRole appUserRole, Date date) {

        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", appUserRole);

        Date now = date;
        Date validity = new Date(now.getTime() + spe.getJwt().getExpiration());

        return Jwts.builder().setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS512,Base64.getEncoder().encodeToString(spe.getJwt().getSecret().getBytes())).compact();
    }

    /**
     * Get authentication
     *
     * @param token
     * @return the authentication
     */
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return this.getClaims(token).getBody().getSubject();
    }

    public String resolveToken(@NotNull HttpServletRequest req) {
        String bearerToken = req.getHeader(spe.getJwt().getHeader());
        if (bearerToken != null && bearerToken.startsWith(spe.getJwt().getHeaderPrefix())) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token){
        try {
            Jws<Claims> claims = this.getClaims(token);
            log.debug("[JwtTokenProvider - tokenExpiration]: "+claims.getBody().getExpiration().toString());

            return !claims.getBody().getExpiration().before(new Date());

        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidJwtAuthenticationException("Expired or invalid JWT token", e);
        }
    }





    private Jws<Claims> getClaims(String token){
        return Jwts.parser().setSigningKey(spe.getJwt().getSecret().getBytes()).parseClaimsJws(token);
    }

    public Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, @NotNull Function<Claims, T> claimsResolver) {
        final Claims claims = getClaims(token).getBody();
        return claimsResolver.apply(claims);
    }

}
