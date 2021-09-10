package com.conferences.service.abstraction;

import com.conferences.entity.User;

public interface IUserService {

    User getUserByLogin(String login);
}
