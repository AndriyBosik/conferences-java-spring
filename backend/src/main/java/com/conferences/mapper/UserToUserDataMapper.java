package com.conferences.mapper;

import com.conferences.entity.User;
import com.conferences.model.UserData;
import org.springframework.stereotype.Component;

@Component
public class UserToUserDataMapper implements IMapper<User, UserData> {

    @Override
    public UserData map(User user) {
        UserData userData = new UserData();
        userData.setLogin(user.getLogin());
        userData.setEmail(user.getEmail());
        userData.setImagePath(user.getImagePath());
        userData.setName(user.getName());
        userData.setSurname(user.getSurname());
        userData.setRole(user.getRole().getTitle());
        return userData;
    }
}
