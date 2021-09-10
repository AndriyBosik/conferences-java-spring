package com.conferences.controller;

import com.conferences.handler.implementation.JwtHandler;
import com.conferences.entity.User;
import com.conferences.model.AuthRequest;
import com.conferences.model.AuthResponse;
import com.conferences.service.abstraction.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final IUserService userService;
    private final JwtHandler jwtHandler;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(IUserService userService, JwtHandler jwtHandler, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtHandler = jwtHandler;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        User user = userService.getUserByLogin(authRequest.getLogin());

        if (user == null || !passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            throw new UsernameNotFoundException(authRequest.getLogin() + " not found");
        }

        String token = jwtHandler.generateToken(user.getLogin());
        return AuthResponse.builder()
            .token(token)
            .username(user.getLogin())
            .role(user.getRole().getTitle())
            .build();
    }
}
