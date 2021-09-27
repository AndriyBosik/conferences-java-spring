package com.conferences.handler.abstraction;

import com.conferences.entity.User;

import java.util.Date;

/**
 * <p>
 *     Defines methods to work with JWT
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/27
 */
public interface IJwtHandler {

    /**
     * <p>Generates 30-minutes valid token</p>
     * @param user information about user to be included in token
     * @return generated token
     */
    String generateToken(User user);

    /**
     * <p>Generates token with custom expiration</p>
     * @param user information about user to be included in token
     * @param expiration custom expiration value
     * @return generated token
     */
    String generateToken(User user, Date expiration);

    /**
     * <p>Validates token</p>
     * @param token token that has to be validated
     * @return true if token is valid, false otherwise
     */
    boolean validateToken(String token);

    /**
     * <p>Extracts user from token</p>
     * @param token token to extract user from
     * @return an instance of {@link User} class
     */
    User getUserFromToken(String token);
}
