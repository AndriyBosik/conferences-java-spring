package com.conferences.handler.implementation;

import com.conferences.handler.abstraction.IJwtHandler;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
@Log4j2
public class JwtHandler implements IJwtHandler {

    private static final String JWT_SECRET = "jwt_secret";

    public String generateToken(String login) {
        return generateToken(login, Date.from(LocalDateTime.now().plusDays(1).atZone(ZoneId.systemDefault()).toInstant()));
    }

    public String generateToken(String login, Date expiration) {
        return Jwts.builder()
            .setSubject(login)
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

    public String getLoginFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
