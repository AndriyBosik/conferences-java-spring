package com.conferences.service.implementation;

import com.conferences.entity.Role;
import com.conferences.entity.User;
import com.conferences.mapper.IMapper;
import com.conferences.model.UserRegistrationData;
import com.conferences.repository.IRoleRepository;
import com.conferences.repository.IUserRepository;
import com.conferences.service.abstraction.IRegistrationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class RegistrationService implements IRegistrationService {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final IMapper<UserRegistrationData, User> mapper;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(IUserRepository userRepository, IRoleRepository roleRepository, IMapper<UserRegistrationData, User> mapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User signUpUser(UserRegistrationData userRegistrationData) {
        log.info("Trying to sign up user");
        if (userRepository.findByLoginOrEmail(userRegistrationData.getLogin(), userRegistrationData.getEmail()) != null) {
            log.warn("User with such email or login already exists");
            return null;
        }
        Role role = roleRepository.findByTitle(userRegistrationData.getRole());
        User user = mapper.map(userRegistrationData);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(role);
        log.info("Saving user to database");
        return userRepository.save(user);
    }
}
