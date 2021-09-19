package com.conferences.handler.abstraction;

import com.conferences.model.UserData;

import java.util.Date;

public interface IJwtHandler {

    String generateToken(String login);

    String generateToken(String login, Date expiration);

    boolean validateToken(String token);

    String getLoginFromToken(String token);
}
