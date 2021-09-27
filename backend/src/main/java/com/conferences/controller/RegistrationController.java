package com.conferences.controller;

import com.conferences.model.UserRegistrationData;
import com.conferences.service.abstraction.IRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <p>
 *     Controller which contains routes to handle user registration
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/27
 */
@RestController
@RequestMapping("/api/sign-up")
public class RegistrationController {

    private final IRegistrationService registrationService;

    @Autowired
    public RegistrationController(IRegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    /**
     * <p>Register user</p>
     * @param userRegistrationData contains information from registration page
     * @return true if user was successfully registered, false otherwise
     */
    @PostMapping("")
    public boolean signUp(@Valid @RequestBody UserRegistrationData userRegistrationData) {
        return registrationService.signUpUser(userRegistrationData) != null;
    }
}
