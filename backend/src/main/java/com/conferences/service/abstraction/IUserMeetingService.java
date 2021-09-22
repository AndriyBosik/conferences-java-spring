package com.conferences.service.abstraction;

import com.conferences.entity.UserMeeting;
import com.conferences.entity.projection.IUserPresence;

import java.util.List;

public interface IUserMeetingService {

    List<IUserPresence> getJoinedUsersByMeeting(int meetingId);

    boolean joinUserToMeeting(UserMeeting userMeeting);
}
