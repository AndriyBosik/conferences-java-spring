package com.conferences.service.implementation;

import com.conferences.entity.UserMeeting;
import com.conferences.entity.projection.IUserPresence;
import com.conferences.repository.IUserMeetingRepository;
import com.conferences.service.abstraction.IUserMeetingService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * {@inheritDoc}
 */
@Log4j2
@Service
public class UserMeetingService implements IUserMeetingService {

    private final IUserMeetingRepository userMeetingRepository;

    @Autowired
    public UserMeetingService(IUserMeetingRepository userMeetingRepository) {
        this.userMeetingRepository = userMeetingRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IUserPresence> getJoinedUsersByMeeting(int meetingId) {
        log.info("Getting users by meeting");
        return new ArrayList<>(userMeetingRepository.findAllByMeeting(meetingId));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean joinUserToMeeting(UserMeeting userMeeting) {
        log.info("Joining user to meeting");
        userMeeting.setPresent(false);
        userMeetingRepository.save(userMeeting);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean editUserPresence(UserMeeting userMeeting) {
        log.info("Editing user presence");
        userMeetingRepository.save(userMeeting);
        return true;
    }
}
