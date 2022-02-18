package com.tech.leasing.service;


import com.tech.leasing.dto.UserDTO;
import com.tech.leasing.model.User;
import com.tech.leasing.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImplementation implements UserService{
  
   
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    public UserServiceImplementation(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public User getUserByPhone(String phone) {
        log.info("getting user with phone: {}", phone);
        return userRepository.getUserByPhone(phone)
                .orElseThrow(()-> new UsernameNotFoundException("such username doesn't exists"));
    }

    @Override
    public User registerUser(UserDTO registerDTO) {
        User user = new User();
        user.setName(registerDTO.getName());
        user.setPhone(registerDTO.getPhone());
        user.setUniqueNumber(registerDTO.getUniqueNumber());
        user.setRole("ROLE_USER");
        if(registerDTO.getPassword().equals(registerDTO.getConfirmPassword())){
            user.setPassword(bCryptPasswordEncoder.encode(registerDTO.getPassword()));
        }
        log.info("saving user with phone number {}", registerDTO.getPhone());
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Principal principal, UserDTO userDTO) {
        User user = getUserFromPrincipal(principal);
        user.setName(userDTO.getName());
        if(userDTO.getPassword().equals(userDTO.getConfirmPassword())){
            user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        }
        user.setUniqueNumber(userDTO.getUniqueNumber());
        user.setUpdatedDate(LocalDateTime.now());
        log.info("Updating user with phone number {}", userDTO.getPhone());
        return user;
    }

    private User getUserFromPrincipal(Principal principal) {
        User user = userRepository.getUserByPhone(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Such username doesn't exist"));
        return user;
    }
}
