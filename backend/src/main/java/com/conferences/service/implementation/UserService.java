package com.conferences.service.implementation;

import com.conferences.entity.User;
import com.conferences.entity.projection.IUserPresence;
import com.conferences.entity.projection.IUserPublicData;
import com.conferences.handler.abstraction.IJwtHandler;
import com.conferences.mapper.IMapper;
import com.conferences.model.UserPublicData;
import com.conferences.model.UserUpdateData;
import com.conferences.repository.IUserMeetingRepository;
import com.conferences.repository.IUserRepository;
import com.conferences.service.abstraction.ISecurityService;
import com.conferences.service.abstraction.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final IMapper<User, UserPublicData> mapper;
    private final ISecurityService securityService;
    private final PasswordEncoder passwordEncoder;
    private final IJwtHandler jwtHandler;

    @Autowired
    public UserService(IUserRepository userRepository, IMapper<User, UserPublicData> mapper, ISecurityService securityService, PasswordEncoder passwordEncoder, IJwtHandler jwtHandler) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.securityService = securityService;
        this.passwordEncoder = passwordEncoder;
        this.jwtHandler = jwtHandler;
    }

    @Override
    public User getUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public List<UserPublicData> getUsersByRole(String role) {
        return userRepository.findAllByRole(role).stream()
            .map(mapper::map)
            .collect(Collectors.toList());
    }

    @Override
    public List<UserPublicData> getAvailableSpeakersByTopic(int topicId) {
        return userRepository.findAvailableSpeakersByTopic(topicId).stream()
            .map(mapper::map)
            .collect(Collectors.toList());
    }

    @Override
    public List<IUserPublicData> getProposedSpeakersForTopic(int topicId) {
        return new ArrayList<>(userRepository.findProposedSpeakersForTopic(topicId));
    }

    @Override
    public String updateUser(UserUpdateData userUpdateData) {
        User user = userRepository.findByLogin(securityService.getUserLogin());
        user.setLogin(userUpdateData.getLogin());
        user.setName(userUpdateData.getName());
        user.setSurname(userUpdateData.getSurname());
        user.setEmail(userUpdateData.getEmail());
        if (!updateUserPassword(user, userUpdateData)) {
            System.out.println(userUpdateData.getOldPassword());
            System.out.println(userUpdateData.getNewPassword());
            System.out.println(userUpdateData.getConfirmPassword());
            return "";
        }
        userRepository.save(user);
        securityService.reAuthenticateUser(user);
        return jwtHandler.generateToken(user.getLogin());
    }

    private boolean updateUserPassword(User user, UserUpdateData userUpdateData) {
        if ("".equals(userUpdateData.getOldPassword()) && "".equals(userUpdateData.getNewPassword())) {
            System.out.println("Phase 1");
            return true;
        }
        if (!passwordEncoder.matches(userUpdateData.getOldPassword(), user.getPassword())) {
            return false;
        }
        if (userUpdateData.getNewPassword().length() < 5) {
            System.out.println("Phase 3");
            return false;
        }
        System.out.println("Phase 3");
        user.setPassword(passwordEncoder.encode(userUpdateData.getNewPassword()));
        return true;
    }
}
