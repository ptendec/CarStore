package com.tech.leasing.service;

import com.tech.leasing.dto.UserDTO;
import com.tech.leasing.model.User;

import java.security.Principal;

public interface UserService {
    User registerUser(UserDTO userDTO);
    User updateUser(Principal principal, UserDTO userDTO);
    User getUserByPhone(String phone);
}
