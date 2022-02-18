package com.tech.leasing.security;

import com.tech.leasing.model.User;
import com.tech.leasing.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CustomAuthentication implements AuthenticationProvider {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public CustomAuthentication(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        User user = userRepository.getUserByPhone(username)
                .orElseThrow(() -> new RuntimeException("such user doesn't exist"+ username));
        if(passwordEncoder.matches(password,user.getPassword())) {
            List<GrantedAuthority> authorityList = new ArrayList<>();
            authorityList.add(new SimpleGrantedAuthority(user.getRole()));
            log.info("Authenticating user");
            return new UsernamePasswordAuthenticationToken(username, password, authorityList);
        }
        else {
            log.error("Authentication failed due to bad credentials");
            throw  new BadCredentialsException("authentication failed");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
