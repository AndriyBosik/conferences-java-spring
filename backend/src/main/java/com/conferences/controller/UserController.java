package com.conferences.controller;

import com.conferences.entity.User;
import com.conferences.model.UserData;
import com.conferences.repository.IUserRepository;
import com.conferences.service.abstraction.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{login}")
    public User getUserById(@PathVariable String login) {
        return userService.getUserByLogin(login);
    }

    @GetMapping("/speakers")
    public List<UserData> getSpeakers() {
        return userService.getUsersByRole("speaker");
    }
}
