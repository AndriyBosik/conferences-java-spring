package com.conferences.controller;

import com.conferences.handler.implementation.JwtHandler;
import com.conferences.entity.User;
import com.conferences.mapper.IMapper;
import com.conferences.model.AuthRequest;
import com.conferences.model.AuthResponse;
import com.conferences.model.UserData;
import com.conferences.service.abstraction.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final IUserService userService;
    private final JwtHandler jwtHandler;
    private final PasswordEncoder passwordEncoder;
    private final IMapper<User, UserData> mapper;

    @Autowired
    public AuthController(IUserService userService, JwtHandler jwtHandler, PasswordEncoder passwordEncoder, IMapper<User, UserData> mapper) {
        this.userService = userService;
        this.jwtHandler = jwtHandler;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        User user = userService.getUserByLogin(authRequest.getLogin());

        if (user == null || !passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            throw new UsernameNotFoundException(authRequest.getLogin() + " not found");
        }

        String accessToken = jwtHandler.generateToken(user.getLogin());
        String refreshToken = jwtHandler.generateToken(user.getLogin(), Date.from(LocalDateTime.now().plusDays(60).atZone(ZoneId.systemDefault()).toInstant()));

        return AuthResponse.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .userData(mapper.map(user))
            .build();
    }
}
