package com.tech.leasing.facade.response;

import lombok.Data;

@Data
public class JWTResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String firstname;
    private String role;
    private String phone;

    public JWTResponse(String accessToken, Long id, String firstname,String role) {
        this.token = accessToken;
        this.id = id;
        this.firstname = firstname;
        this.role = role;
    }

    public JWTResponse() {
    }
}
