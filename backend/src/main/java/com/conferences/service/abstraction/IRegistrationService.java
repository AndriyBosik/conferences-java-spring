package com.conferences.service.abstraction;

import com.conferences.entity.User;
import com.conferences.model.UserRegistrationData;

/**
 * <p>
 *     Defines methods to work with registration
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/27
 */
public interface IRegistrationService {

    /**
     * <p>Signs up user</p>
     * @param userRegistrationData contains information about user which has to be registered
     * @return an instance of {@link User} class or null if there was some error during registration
     */
    User signUpUser(UserRegistrationData userRegistrationData);
}
