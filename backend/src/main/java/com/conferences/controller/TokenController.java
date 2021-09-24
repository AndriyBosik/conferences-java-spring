package com.conferences.controller;

import com.conferences.entity.User;
import com.conferences.handler.abstraction.IJwtHandler;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/token")
public class TokenController {

    private final IJwtHandler jwtHandler;

    public TokenController(IJwtHandler jwtHandler) {
        this.jwtHandler = jwtHandler;
    }

    @PostMapping("/refresh")
    public String refreshToken(@CookieValue("refreshToken") String refreshToken) {
        if (refreshToken != null && jwtHandler.validateToken(refreshToken)) {
            User user = jwtHandler.getUserFromToken(refreshToken);
            return jwtHandler.generateToken(user);
        }
        return null;
    }
}
