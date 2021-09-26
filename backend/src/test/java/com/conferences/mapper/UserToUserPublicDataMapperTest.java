package com.conferences.mapper;

import com.conferences.entity.Role;
import com.conferences.entity.User;
import com.conferences.model.UserPublicData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserToUserPublicDataMapperTest {

    private static UserToUserPublicDataMapper mapper;

    @BeforeAll
    private static void beforeAll() {
        mapper = new UserToUserPublicDataMapper();
    }

    @Test
    public void shouldMapUserPublicDataToUser() {
        User user = new User();
        user.setId(5);
        user.setLogin("login");
        user.setName("username");
        user.setSurname("surname");
        user.setEmail("email@example.com");
        user.setImagePath("example.image.path");
        user.setRole(new Role(1, "moderator"));

        UserPublicData data = mapper.map(user);

        assertEquals(5, data.getId());
        assertEquals("login", data.getLogin());
        assertEquals("username", data.getName());
        assertEquals("surname", data.getSurname());
        assertEquals("email@example.com", data.getEmail());
        assertEquals("example.image.path", data.getImagePath());
        assertEquals("moderator", data.getRole());
    }
}
