package com.conferences.service.abstraction;

import com.conferences.entity.User;
import com.conferences.model.UserRegistrationData;

public interface IRegistrationService {

    User signUpUser(UserRegistrationData userRegistrationData);
}
