package com.conferences.service.implementation;

import com.conferences.entity.User;
import com.conferences.entity.projection.IUserPresence;
import com.conferences.entity.projection.IUserPublicData;
import com.conferences.mapper.IMapper;
import com.conferences.model.UserData;
import com.conferences.repository.IUserMeetingRepository;
import com.conferences.repository.IUserRepository;
import com.conferences.service.abstraction.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final IUserMeetingRepository userMeetingRepository;
    private final IMapper<User, UserData> mapper;

    @Autowired
    public UserService(IUserRepository userRepository, IUserMeetingRepository userMeetingRepository, IMapper<User, UserData> mapper) {
        this.userRepository = userRepository;
        this.userMeetingRepository = userMeetingRepository;
        this.mapper = mapper;
    }

    @Override
    public User getUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public List<UserData> getUsersByRole(String role) {
        return userRepository.findAllByRole(role).stream()
            .map(mapper::map)
            .collect(Collectors.toList());
    }

    @Override
    public List<UserData> getAvailableSpeakersByTopic(int topicId) {
        return userRepository.findAvailableSpeakersByTopic(topicId).stream()
            .map(mapper::map)
            .collect(Collectors.toList());
    }

    @Override
    public List<IUserPublicData> getProposedSpeakersForTopic(int topicId) {
        return new ArrayList<>(userRepository.findProposedSpeakersForTopic(topicId));
    }

    @Override
    public List<IUserPresence> getJoinedUsersByMeeting(int meetingId) {
        return new ArrayList<>(userMeetingRepository.findAllByMeeting(meetingId));
    }
}
