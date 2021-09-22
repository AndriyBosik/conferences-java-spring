package com.conferences.service.implementation;

import com.conferences.entity.UserMeeting;
import com.conferences.entity.projection.IUserPresence;
import com.conferences.repository.IUserMeetingRepository;
import com.conferences.service.abstraction.IUserMeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserMeetingService implements IUserMeetingService {

    private final IUserMeetingRepository userMeetingRepository;

    @Autowired
    public UserMeetingService(IUserMeetingRepository userMeetingRepository) {
        this.userMeetingRepository = userMeetingRepository;
    }

    @Override
    public List<IUserPresence> getJoinedUsersByMeeting(int meetingId) {
        return new ArrayList<>(userMeetingRepository.findAllByMeeting(meetingId));
    }

    @Override
    public boolean joinUserToMeeting(UserMeeting userMeeting) {
        userMeeting.setPresent(false);
        userMeetingRepository.save(userMeeting);
        return true;
    }
}
