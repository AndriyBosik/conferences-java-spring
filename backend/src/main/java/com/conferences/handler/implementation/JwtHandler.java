package com.conferences.handler.implementation;

import com.conferences.entity.User;
import com.conferences.handler.abstraction.IJwtHandler;
import com.conferences.handler.abstraction.IPrivateDataHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
@Log4j2
public class JwtHandler implements IJwtHandler {

    private static final String JWT_SECRET = "jwt_secret";

    private final ObjectMapper objectMapper;
    private final IPrivateDataHandler<User> userPrivateDataHandler;

    @Autowired
    public JwtHandler(ObjectMapper objectMapper, IPrivateDataHandler<User> userPrivateDataHandler) {
        this.objectMapper = objectMapper;
        this.userPrivateDataHandler = userPrivateDataHandler;
    }

    public String generateToken(User user) {
        return generateToken(user, Date.from(LocalDateTime.now().plusDays(1).atZone(ZoneId.systemDefault()).toInstant()));
    }

    public String generateToken(User user, Date expiration) {
        String jsonUser = "";
        try {
            userPrivateDataHandler.clearPrivateData(user);
            jsonUser = objectMapper.writeValueAsString(user);
        } catch (JsonProcessingException exception) {
            exception.printStackTrace();
        }

        return Jwts.builder()
            .setSubject(jsonUser)
            .setExpiration(expiration)
            .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
            .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    public User getUserFromToken(String token) {
        String jsonUser = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody().getSubject();
        User user = null;
        try {
            user = objectMapper.readValue(jsonUser, User.class);
        } catch (JsonProcessingException exception) {
            exception.printStackTrace();
        }
        return user;
    }
}
