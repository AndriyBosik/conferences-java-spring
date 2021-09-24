package com.conferences.controller;

import com.conferences.handler.abstraction.IJwtHandler;
import com.conferences.entity.User;
import com.conferences.mapper.IMapper;
import com.conferences.model.AuthRequest;
import com.conferences.model.AuthResponse;
import com.conferences.model.UserPublicData;
import com.conferences.service.abstraction.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final IUserService userService;
    private final IJwtHandler jwtHandler;
    private final PasswordEncoder passwordEncoder;
    private final IMapper<User, UserPublicData> mapper;

    @Autowired
    public AuthController(IUserService userService, IJwtHandler jwtHandler, PasswordEncoder passwordEncoder, IMapper<User, UserPublicData> mapper) {
        this.userService = userService;
        this.jwtHandler = jwtHandler;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest, HttpServletResponse response) {
        User user = userService.getUserByLogin(authRequest.getLogin());

        if (user == null || !passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            throw new UsernameNotFoundException(authRequest.getLogin() + " not found");
        }

        String accessToken = jwtHandler.generateToken(user);
        String refreshToken = jwtHandler.generateToken(user, Date.from(LocalDateTime.now().plusDays(60).atZone(ZoneId.systemDefault()).toInstant()));

        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setMaxAge(60 * 24 * 60 * 60);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setHttpOnly(true);
        response.addCookie(refreshTokenCookie);

        return ResponseEntity.ok(
                AuthResponse.builder()
                    .accessToken(accessToken)
                    .build());
    }

    @PostMapping("/logout")
    public boolean logout(@CookieValue("refreshToken") Cookie cookie, HttpServletResponse response) {
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return true;
    }
}
