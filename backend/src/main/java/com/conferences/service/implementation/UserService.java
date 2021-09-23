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

    @Autowired
    public UserService(IUserRepository userRepository, IMapper<User, UserPublicData> mapper, ISecurityService securityService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.securityService = securityService;
        this.passwordEncoder = passwordEncoder;
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
            return "";
        }
        userRepository.save(user);
        return securityService.reAuthenticateUser(user);
    }

    private boolean updateUserPassword(User user, UserUpdateData userUpdateData) {
        if ("".equals(userUpdateData.getOldPassword()) && "".equals(userUpdateData.getNewPassword())) {
            return true;
        }
        if (!passwordEncoder.matches(userUpdateData.getOldPassword(), user.getPassword())) {
            return false;
        }
        if (userUpdateData.getNewPassword().length() < 5) {
            return false;
        }
        user.setPassword(passwordEncoder.encode(userUpdateData.getNewPassword()));
        return true;
    }

    @Override
    public String getUserEmail() {
        return userRepository.findEmailByUserLogin(securityService.getUserLogin());
    }

    @Override
    public String updateUserImagePath(String imagePath) {
        String login = securityService.getUserLogin();
        userRepository.updateImagePath(login, imagePath);
        User user = userRepository.findByLogin(login);
        return securityService.reAuthenticateUser(user);
    }
}
