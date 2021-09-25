package com.conferences.controller;

import com.conferences.handler.abstraction.IJwtHandler;
import com.conferences.entity.User;
import com.conferences.mapper.IMapper;
import com.conferences.model.AuthRequest;
import com.conferences.model.AuthResponse;
import com.conferences.model.UserPublicData;
import com.conferences.service.abstraction.IUserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Log4j2
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
    public AuthResponse login(@RequestBody AuthRequest authRequest, HttpServletResponse response) {
        User user = userService.getUserByLogin(authRequest.getLogin());

        log.info("Processing user login");
        if (user == null || !passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            log.info("User not found");
            throw new UsernameNotFoundException(authRequest.getLogin() + " not found");
        }

        String accessToken = jwtHandler.generateToken(user);
        String refreshToken = jwtHandler.generateToken(user, Date.from(LocalDateTime.now().plusDays(60).atZone(ZoneId.systemDefault()).toInstant()));

        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        configureAndAddToResponseCookie(refreshTokenCookie, 60 * 24 * 60 * 60, response);

        log.info("Returning response");
        return AuthResponse.builder()
                .accessToken(accessToken)
                .build();
    }

    @PostMapping("/logout")
    public boolean logout(@CookieValue("refreshToken") Cookie cookie, HttpServletResponse response) {
        log.info("Processing logout");
        configureAndAddToResponseCookie(cookie, 0, response);
        log.info("User logged out");
        return true;
    }

    private void configureAndAddToResponseCookie(Cookie cookie, int maxAge, HttpServletResponse response) {
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }
}
