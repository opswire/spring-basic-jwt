package com.jinada.springsecurity.controllers;

import com.jinada.springsecurity.dtos.RegisterUserDTO;
import com.jinada.springsecurity.dtos.RequestLoginDTO;
import com.jinada.springsecurity.services.AuthService;
import com.jinada.springsecurity.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final UserService userService;
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserService userService, AuthService authService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.authService = authService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/auth")
    public ResponseEntity<?> auth(@RequestBody RequestLoginDTO requestLoginDTO) {
        return authService.auth(requestLoginDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<?> signup(@RequestBody RegisterUserDTO registerUserDTO) {
        return userService.createNewUser(registerUserDTO, passwordEncoder);
    }
}
