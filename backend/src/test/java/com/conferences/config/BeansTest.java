package com.conferences.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.junit.jupiter.api.Assertions.*;

public class BeansTest {

    private static Beans beans;

    @BeforeAll
    private static void beforeAll() {
        beans = new Beans();
    }

    @Test
    public void shouldReturnInstanceOfBCryptPasswordEncoder() {
        PasswordEncoder passwordEncoder = beans.passwordEncoder();

        assertEquals(BCryptPasswordEncoder.class, passwordEncoder.getClass());
    }

    @Test
    public void shouldReturnInstanceOfWebMvcConfigurer() {
        WebMvcConfigurer configurer = beans.corsConfigurer();

        assertTrue(configurer instanceof WebMvcConfigurer);
    }
}
