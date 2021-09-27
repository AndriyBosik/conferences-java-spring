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
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * {@inheritDoc}
 */
@Log4j2
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

    /**
     * {@inheritDoc}
     */
    @Override
    public User getUserByLogin(String login) {
        log.info("Getting user by login");
        return userRepository.findByLogin(login);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserPublicData> getUsersByRole(String role) {
        log.info("Getting users by role and mapping to hide private data");
        return userRepository.findAllByRole(role).stream()
            .map(mapper::map)
            .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserPublicData> getAvailableSpeakersByTopic(int topicId) {
        log.info("Getting users which are available to topic and mapping them to hide private data");
        return userRepository.findAvailableSpeakersByTopic(topicId).stream()
            .map(mapper::map)
            .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IUserPublicData> getProposedSpeakersForTopic(int topicId) {
        log.info("Getting speakers which were proposed to report topic");
        return new ArrayList<>(userRepository.findProposedSpeakersForTopic(topicId));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String updateUser(UserUpdateData userUpdateData) {
        log.info("Updating user data");
        User user = userRepository.findByLogin(securityService.getUserLogin());
        user.setLogin(userUpdateData.getLogin());
        user.setName(userUpdateData.getName());
        user.setSurname(userUpdateData.getSurname());
        user.setEmail(userUpdateData.getEmail());
        if (!updateUserPassword(user, userUpdateData)) {
            return "";
        }
        log.info("Saving user new data");
        userRepository.save(user);
        return securityService.reAuthenticateUser(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUserEmail() {
        log.info("Getting authenticated user email");
        return userRepository.findEmailByUserLogin(securityService.getUserLogin());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String updateUserImagePath(String imagePath) {
        log.info("Updating user's image path");
        String login = securityService.getUserLogin();
        userRepository.updateImagePath(login, imagePath);
        User user = userRepository.findByLogin(login);
        return securityService.reAuthenticateUser(user);
    }

    /**
     * <p>Updates user's password</p>
     * @param user user to update password for
     * @param userUpdateData contains information about passwords
     * @return true if password was updated, false otherwise
     */
    private boolean updateUserPassword(User user, UserUpdateData userUpdateData) {
        if ("".equals(userUpdateData.getOldPassword()) && "".equals(userUpdateData.getNewPassword())) {
            return true;
        }
        if (!passwordEncoder.matches(userUpdateData.getOldPassword(), user.getPassword())) {
            log.warn("Passwords do not match");
            return false;
        }
        if (userUpdateData.getNewPassword().length() < 5) {
            log.warn("Password is invalid");
            return false;
        }
        user.setPassword(passwordEncoder.encode(userUpdateData.getNewPassword()));
        log.warn("Password updates successfully");
        return true;
    }
}
