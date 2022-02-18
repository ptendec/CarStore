package com.tech.leasing.dto;


import lombok.Data;

@Data
public class UserDTO {
    private String name;
    private String phone;
    private String password;
    private String confirmPassword;
    private String uniqueNumber;
}
