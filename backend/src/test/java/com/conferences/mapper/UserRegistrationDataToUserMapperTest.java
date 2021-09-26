package com.conferences.mapper;

import com.conferences.entity.User;
import com.conferences.model.UserRegistrationData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserRegistrationDataToUserMapperTest {

    private static UserRegistrationDataToUserMapper mapper;

    @BeforeAll
    private static void beforeAll() {
        mapper = new UserRegistrationDataToUserMapper();
    }

    @Test
    public void shouldMapUserRegistrationDataToUser() {
        UserRegistrationData userData = new UserRegistrationData();
        userData.setLogin("login");
        userData.setName("name");
        userData.setSurname("surname");
        userData.setEmail("email@user.com");
        userData.setPassword("userPassword");

        User user = mapper.map(userData);

        assertEquals("login", user.getLogin());
        assertEquals("name", user.getName());
        assertEquals("surname", user.getSurname());
        assertEquals("email@user.com", user.getEmail());
        assertEquals("userPassword", user.getPassword());
    }
}
