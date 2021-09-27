package com.conferences.service.abstraction;

import com.conferences.entity.User;

/**
 * <p>
 *     Defines methods to work with Spring Security
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/27
 */
public interface ISecurityService {

    /**
     * <p>Returns login of authenticated user</p>
     * @return string value which represents login of authenticated user
     */
    String getUserLogin();

    /**
     * <p>Re-authenticates user in Spring Security Context</p>
     * @param user new re-authenticated user's data
     * @return string value which represents new access token
     */
    String reAuthenticateUser(User user);
}
