package com.conferences.service.abstraction;

import com.conferences.entity.User;

public interface ISecurityService {

    String getUserLogin();

    void reAuthenticateUser(User user);
}
