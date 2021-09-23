package com.conferences.service.abstraction;

import com.conferences.entity.User;

public interface ISecurityService {

    String getUserLogin();

    String reAuthenticateUser(User user);
}
