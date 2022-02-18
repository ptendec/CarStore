package com.tech.leasing.api;


import com.tech.leasing.dto.CarDTO;
import com.tech.leasing.dto.LoginDTO;
import com.tech.leasing.dto.UserDTO;
import com.tech.leasing.facade.UserFacade;
import com.tech.leasing.model.Car;
import com.tech.leasing.model.User;
import com.tech.leasing.security.JWTTokenProvider;
import com.tech.leasing.service.CarService;
import com.tech.leasing.service.CarServiceImplementation;
import com.tech.leasing.service.UserServiceImplementation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RequestMapping("/api/auth/")
@CrossOrigin
@RestController
public class AuthenticationController {
    private final UserServiceImplementation userService;
    private final UserFacade userFacade;
    private final JWTTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final CarServiceImplementation carService;

    public AuthenticationController(UserServiceImplementation userService, UserFacade userFacade, JWTTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager, CarServiceImplementation carService) {
        this.userService = userService;
        this.userFacade = userFacade;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.carService = carService;
    }

    //Данный роут нужен для регистрации пользователя
    @PostMapping("/register")
    ResponseEntity<Object> registerUser(@RequestBody UserDTO userDTO) {
        userService.registerUser(userDTO);
        return new ResponseEntity<>("Successfully registration", HttpStatus.OK);
    }

    // Данный роут нужен для логина в систему, authenticationManager отвечает за проверку логина и пароля пользователя
    // jwtTokenProvider создает токен
    // userFacade нужен для того, чтобы отвечать на сервер определенными переменннами, почти тоже самое, что и DTO
    @PostMapping("/login")
    ResponseEntity<Object> login(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDTO.getPhone(),
                loginDTO.getPassword()
        ));
        User user = userService.getUserByPhone(loginDTO.getPhone());
        String token = jwtTokenProvider.generateToken(authentication);
        return new ResponseEntity<>(userFacade.jwtResponse(user, token), HttpStatus.OK);
    }
}
