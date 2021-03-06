package com.conferences.handler.implementation;

import com.conferences.entity.User;
import com.conferences.handler.abstraction.IJwtHandler;
import com.conferences.handler.abstraction.IPrivateDataHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * {@inheritDoc}
 */
@Log4j2
@Component
public class JwtHandler implements IJwtHandler {

    private static final String JWT_SECRET = "jwt_secret";

    private final ObjectMapper objectMapper;
    private final IPrivateDataHandler<User> userPrivateDataHandler;
    private final int expirationTime;

    @Autowired
    public JwtHandler(
        @Value("${token.expiration}") int expirationTime,
        ObjectMapper objectMapper,
        IPrivateDataHandler<User> userPrivateDataHandler
    ) {
        this.expirationTime = expirationTime;
        this.objectMapper = objectMapper;
        this.userPrivateDataHandler = userPrivateDataHandler;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String generateToken(User user) {
        log.info("Generating token");
        return generateToken(user, Date.from(LocalDateTime.now().plusMinutes(expirationTime).atZone(ZoneId.systemDefault()).toInstant()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String generateToken(User user, Date expiration) {
        log.info("Generating token with custom expiration");
        String jsonUser = "";
        try {
            userPrivateDataHandler.clearPrivateData(user);
            jsonUser = objectMapper.writeValueAsString(user);
        } catch (JsonProcessingException exception) {
            log.error("Unable to parse user object to JSON", exception);
        }

        return Jwts.builder()
            .setSubject(jsonUser)
            .setExpiration(expiration)
            .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
            .compact();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validateToken(String token) {
        try {
            log.info("Validating token");
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
        } catch (Exception exception) {
            log.error("Token is invalid", exception);
            return false;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getUserFromToken(String token) {
        String jsonUser = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody().getSubject();
        User user = null;
        try {
            log.info("Parsing user from token JSON value");
            user = objectMapper.readValue(jsonUser, User.class);
        } catch (JsonProcessingException exception) {
            log.error("Unable to parse", exception);
        }
        return user;
    }
}
