package com.conferences.controller;

import com.conferences.entity.User;
import com.conferences.handler.abstraction.IJwtHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/token")
public class TokenController {

    private final IJwtHandler jwtHandler;

    public TokenController(IJwtHandler jwtHandler) {
        this.jwtHandler = jwtHandler;
    }

    @GetMapping("/refresh")
    public String refreshToken(@RequestBody String refreshToken) {
        User user = jwtHandler.getUserFromToken(refreshToken);
        return jwtHandler.generateToken(user);
    }
}
