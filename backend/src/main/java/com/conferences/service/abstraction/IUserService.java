package com.conferences.service.abstraction;

import com.conferences.entity.User;
import com.conferences.model.UserData;

import java.util.List;

public interface IUserService {

    User getUserByLogin(String login);

    List<UserData> getUsersByRole(String role);
}
