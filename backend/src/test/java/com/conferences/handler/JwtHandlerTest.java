package com.conferences.handler;

import com.conferences.entity.Role;
import com.conferences.entity.User;
import com.conferences.handler.abstraction.IPrivateDataHandler;
import com.conferences.handler.implementation.JwtHandler;
import com.conferences.handler.implementation.UserPrivateDataHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class JwtHandlerTest {

    private static User user;
    private static JwtHandler jwtHandler;
    private static ObjectMapper objectMapper;
    private static IPrivateDataHandler<User> userPrivateDataHandler;

    @BeforeAll
    private static void beforeAll() {
        objectMapper = new ObjectMapper();
        userPrivateDataHandler = new UserPrivateDataHandler();
        jwtHandler = new JwtHandler(objectMapper, userPrivateDataHandler);
    }

    @BeforeEach
    private void beforeEach() {
        user = new User();
        user.setLogin("login");
        user.setName("name");
        user.setSurname("surname");
        user.setPassword("password");
        user.setEmail("user@email.com");
        user.setImagePath("image_path.png");
        user.setRole(new Role(1, "speaker"));
    }

    @Test
    public void shouldGenerateTokenFor30Minutes() {
        String token = jwtHandler.generateToken(user);
        String tokenOnly = token.substring(0, token.lastIndexOf('.') + 1);
        Date expiration = ((Claims) Jwts.parser().parse(tokenOnly).getBody()).getExpiration();
        Date now = new Date();

        long duration = expiration.getTime() - now.getTime();

        assertTrue(TimeUnit.MILLISECONDS.toMinutes(duration) <= 30);
        assertTrue(TimeUnit.MILLISECONDS.toMinutes(duration) >= 29);
    }

    @Test
    public void shouldGenerateTokenFor40Days() {
        Date date = Date.from(LocalDateTime.now().plusDays(40).atZone(ZoneId.systemDefault()).toInstant());
        String token = jwtHandler.generateToken(user, date);
        String tokenOnly = token.substring(0, token.lastIndexOf('.') + 1);
        Date expiration = ((Claims) Jwts.parser().parse(tokenOnly).getBody()).getExpiration();
        Date now = new Date();

        long duration = expiration.getTime() - now.getTime();

        assertTrue(TimeUnit.MILLISECONDS.toDays(duration) <= 40);
        assertTrue(TimeUnit.MILLISECONDS.toDays(duration) >= 39);
    }

    @Test
    public void shouldValidateToken() {
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ7XCJpZFwiOm51bGwsXCJsb2dpblwiOlwibG9naW5cIixcInBhc3N3b3JkXCI6XCJcIixcInN1cm5hbWVcIjpcInN1cm5hbWVcIixcIm5hbWVcIjpcIm5hbWVcIixcImVtYWlsXCI6XCJcIixcImltYWdlUGF0aFwiOlwiaW1hZ2VfcGF0aC5wbmdcIixcInJvbGVcIjp7XCJpZFwiOjEsXCJ0aXRsZVwiOlwic3BlYWtlclwifX0iLCJleHAiOjMzMTg5NTU1Mjc0fQ.4nj-9UVEpmZfYiOYzof9XtZC0hm0fX0eNf_1_9Cc-TWmeeyKCpnLI5P04ey4m1J7r_20TkQ6HBacXR7OKEN5uA";

        assertTrue(jwtHandler.validateToken(token));
    }

    @Test
    public void shouldInvalidateToken() {
        String token = "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJzdWIiOnsibG9naW4iOiJsb2dpbiIsIm5hbWUiOiJuYW1lIiwic3VybmFtZSI6InN1cm5hbWUiLCJwYXNzd29yZCI6InBhc3N3b3JkIiwiZW1haWwiOiJ1c2VyQGVtYWlsLmNvbSIsImltYWdlUGF0aCI6ImlhbWdlX3BhdGgucG5nIiwicm9sZSI6eyJpZCI6MSwidGl0bGUiOiJzcGVha2VyIn19LCJleHAiOjE2MzI2NDU3NTAyNzB9.hSgZBxkR-F17t7YphYYsRid7FuBFgBpBQ0lkgxPWLAVzM1WIBMS9-AGtQ8tGJNo2A1LnFaJ91v25lrWLPpn94g";

        assertFalse(jwtHandler.validateToken(token));
    }

    @Test
    public void shouldParseUserFromToken() {
        User parsedUser = jwtHandler.getUserFromToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ7XCJpZFwiOm51bGwsXCJsb2dpblwiOlwibG9naW5cIixcInBhc3N3b3JkXCI6XCJcIixcInN1cm5hbWVcIjpcInN1cm5hbWVcIixcIm5hbWVcIjpcIm5hbWVcIixcImVtYWlsXCI6XCJcIixcImltYWdlUGF0aFwiOlwiaW1hZ2VfcGF0aC5wbmdcIixcInJvbGVcIjp7XCJpZFwiOjEsXCJ0aXRsZVwiOlwic3BlYWtlclwifX0iLCJleHAiOjMzMTg5NTU1Mjc0fQ.4nj-9UVEpmZfYiOYzof9XtZC0hm0fX0eNf_1_9Cc-TWmeeyKCpnLI5P04ey4m1J7r_20TkQ6HBacXR7OKEN5uA");

        assertEquals("login", parsedUser.getLogin());
        assertEquals("name", parsedUser.getName());
        assertEquals("surname", parsedUser.getSurname());
        assertEquals("", parsedUser.getEmail());
        assertEquals("", parsedUser.getPassword());
        assertEquals("speaker", parsedUser.getRole().getTitle());
        assertEquals("image_path.png", parsedUser.getImagePath());
    }
}
