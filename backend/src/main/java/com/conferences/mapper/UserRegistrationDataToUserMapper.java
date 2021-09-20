package com.conferences.mapper;

import com.conferences.entity.User;
import com.conferences.model.UserRegistrationData;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationDataToUserMapper implements IMapper<UserRegistrationData, User> {

    @Override
    public User map(UserRegistrationData model) {
        User user = new User();

        user.setLogin(model.getLogin());
        user.setEmail(model.getEmail());
        user.setName(model.getName());
        user.setSurname(model.getSurname());
        user.setPassword(model.getPassword());

        return user;
    }
}
