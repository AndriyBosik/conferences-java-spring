package com.conferences.mapper;

import com.conferences.entity.User;
import com.conferences.model.UserRegistrationData;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class UserRegistrationDataToUserMapper implements IMapper<UserRegistrationData, User> {

    @Override
    public User map(UserRegistrationData model) {
        log.info("Mapping {} to {}", UserRegistrationData.class.getTypeName(), User.class.getTypeName());
        User user = new User();

        user.setLogin(model.getLogin());
        user.setEmail(model.getEmail());
        user.setName(model.getName());
        user.setSurname(model.getSurname());
        user.setPassword(model.getPassword());

        return user;
    }
}
