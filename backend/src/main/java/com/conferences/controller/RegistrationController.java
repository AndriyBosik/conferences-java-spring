package com.conferences.controller;

import com.conferences.model.UserRegistrationData;
import com.conferences.service.abstraction.IRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/sign-up")
public class RegistrationController {

    private final IRegistrationService registrationService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationController(IRegistrationService registrationService, PasswordEncoder passwordEncoder) {
        this.registrationService = registrationService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("")
    public boolean signUp(@Valid @RequestBody UserRegistrationData userRegistrationData) {
        return registrationService.signUpUser(userRegistrationData) != null;
    }
}
