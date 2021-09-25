package com.conferences.controller;

import com.conferences.entity.User;
import com.conferences.handler.abstraction.IJwtHandler;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/api/token")
public class TokenController {

    private final IJwtHandler jwtHandler;

    public TokenController(IJwtHandler jwtHandler) {
        this.jwtHandler = jwtHandler;
    }

    @PostMapping("/refresh")
    public String refreshToken(@CookieValue("refreshToken") String refreshToken) {
        log.info("Refreshing access token");
        if (refreshToken != null && jwtHandler.validateToken(refreshToken)) {
            User user = jwtHandler.getUserFromToken(refreshToken);
            return jwtHandler.generateToken(user);
        }
        return null;
    }
}
