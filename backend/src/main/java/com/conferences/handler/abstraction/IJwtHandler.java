package com.conferences.handler.abstraction;

public interface IJwtHandler {

    String generateToken(String login);

    boolean validateToken(String token);

    String getLoginFromToken(String token);
}
