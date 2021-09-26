package com.conferences.handler;

import com.conferences.entity.User;
import com.conferences.handler.implementation.UserPrivateDataHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserPrivateDataHandlerTest {

    private static UserPrivateDataHandler dataHandler;

    @BeforeAll
    private static void beforeAll() {
        dataHandler = new UserPrivateDataHandler();
    }

    @Test
    public void shouldClearUserPrivateData() {
        User user = new User();
        user.setLogin("user_login");
        user.setEmail("user@email.com");
        user.setName("userName");
        user.setSurname("userSurname");
        user.setPassword("userPassword");
        user.setImagePath("image_path.png");

        dataHandler.clearPrivateData(user);

        assertEquals("user_login", user.getLogin());
        assertEquals("", user.getEmail());
        assertEquals("userName", user.getName());
        assertEquals("userSurname", user.getSurname());
        assertEquals("", user.getPassword());
        assertEquals("image_path.png", user.getImagePath());
    }
}
