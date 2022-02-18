package com.tech.leasing.dto;

import lombok.Data;

@Data
public class RegisterDTO {
    private String name;
    private String phone;
    private String uniqueNumber;
    private String password;
    private String confirmPassword;
}
