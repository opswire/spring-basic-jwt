package com.jinada.springsecurity.services;

import com.jinada.springsecurity.dtos.RequestLoginDTO;
import com.jinada.springsecurity.dtos.ResponseLoginDTO;
import com.jinada.springsecurity.errors.AppError;
import com.jinada.springsecurity.utils.JwtTokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AuthService {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    public AuthService(UserService userService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtTokenUtil jwtTokenUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public ResponseEntity<?> auth(@RequestBody RequestLoginDTO requestLoginDTO) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                            requestLoginDTO.getUsername(), requestLoginDTO.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(
                    new AppError(HttpStatus.UNAUTHORIZED.value(), "Incorrect password or login"),
                    HttpStatus.UNAUTHORIZED
            );
        }
        UserDetails userDetails = userService.loadUserByUsername(requestLoginDTO.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new ResponseLoginDTO(token));
    }
}
