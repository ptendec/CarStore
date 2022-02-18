package com.tech.leasing.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
public class JWTTokenProvider {
    public String generateToken(Authentication authentication) {
        Date currentTime = new Date(System.currentTimeMillis());
        Date expirationDate = new Date(currentTime.getTime() + SecurityConstants.EXPIRATION_TIME);

        SecretKey secretKey = Keys.hmacShaKeyFor(SecurityConstants.SECRET.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .setSubject("JWT TOKEN")
                .claim("username",authentication.getName())
                .claim("roles",divideAuthorities(authentication.getAuthorities()))
                .setIssuedAt(currentTime)
                .setExpiration(expirationDate)
                .signWith(secretKey)
                .compact();
    }
    private String divideAuthorities(Collection<? extends GrantedAuthority> collection) {
        Set<String> authorities = new HashSet<>();
        for(GrantedAuthority authority : collection) {
            authorities.add(authority.getAuthority());
        }
        return String.join(",", authorities);
    }
}
