package com.conferences.mapper;

import com.conferences.entity.User;
import com.conferences.model.UserPublicData;
import org.springframework.stereotype.Component;

@Component
public class UserToUserPublicDataMapper implements IMapper<User, UserPublicData> {

    @Override
    public UserPublicData map(User user) {
        UserPublicData userPublicData = new UserPublicData();
        userPublicData.setId(user.getId());
        userPublicData.setLogin(user.getLogin());
        userPublicData.setEmail(user.getEmail());
        userPublicData.setImagePath(user.getImagePath());
        userPublicData.setName(user.getName());
        userPublicData.setSurname(user.getSurname());
        userPublicData.setRole(user.getRole().getTitle());
        return userPublicData;
    }
}
