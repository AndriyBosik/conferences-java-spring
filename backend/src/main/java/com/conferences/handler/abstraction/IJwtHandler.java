package com.conferences.handler.abstraction;

import com.conferences.entity.User;

import java.util.Date;

public interface IJwtHandler {

    String generateToken(User user);

    String generateToken(User user, Date expiration);

    boolean validateToken(String token);

    User getUserFromToken(String token);
}
