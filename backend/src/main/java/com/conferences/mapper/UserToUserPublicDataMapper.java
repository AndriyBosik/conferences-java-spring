package com.conferences.mapper;

import com.conferences.entity.User;
import com.conferences.model.UserPublicData;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

/**
 * {@inheritDoc}
 */
@Log4j2
@Component
public class UserToUserPublicDataMapper implements IMapper<User, UserPublicData> {

    /**
     * {@inheritDoc}
     * <p>Maps an instance of {@link User} class to an instance of {@link UserPublicData} class</p>
     */
    @Override
    public UserPublicData map(User user) {
        log.info("Mapping {} to {}", User.class.getTypeName(), UserPublicData.class.getTypeName());
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
