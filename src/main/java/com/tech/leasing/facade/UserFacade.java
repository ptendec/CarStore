package com.tech.leasing.facade;

import com.tech.leasing.dto.LoginDTO;
import com.tech.leasing.facade.response.JWTResponse;
import com.tech.leasing.model.User;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
public class UserFacade {
    public JWTResponse jwtResponse(User user, String token) {
        JWTResponse jwtResponse = new JWTResponse();
        jwtResponse.setFirstname(user.getName());
        jwtResponse.setPhone(user.getPhone());
        jwtResponse.setRole(user.getRole());
        jwtResponse.setType("Bearer ");
        jwtResponse.setToken(token);
        return jwtResponse;
    }
}
