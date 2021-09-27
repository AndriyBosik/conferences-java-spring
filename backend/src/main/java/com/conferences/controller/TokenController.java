package com.conferences.controller;

import com.conferences.entity.User;
import com.conferences.handler.abstraction.IJwtHandler;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *     Controller which contains routes to handle toke requests
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/27
 */
@Log4j2
@RestController
@RequestMapping("/api/token")
public class TokenController {

    private final IJwtHandler jwtHandler;

    public TokenController(IJwtHandler jwtHandler) {
        this.jwtHandler = jwtHandler;
    }

    /**
     * <p>Refreshes access token</p>
     * @param refreshToken cookie value(must be valid to return new access token)
     * @return new access token or null in case of any error
     */
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
