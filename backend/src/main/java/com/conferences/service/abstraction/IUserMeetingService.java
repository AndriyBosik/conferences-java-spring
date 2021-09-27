package com.conferences.service.abstraction;

import com.conferences.entity.UserMeeting;
import com.conferences.entity.projection.IUserPresence;

import java.util.List;

/**
 * <p>
 *     Defines methods to work with user's meetings
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/27
 */
public interface IUserMeetingService {

    /**
     * <p>Returns user presence information for meeting</p>
     * @param meetingId id of meeting
     * @return set of {@link IUserPresence} objects
     */
    List<IUserPresence> getJoinedUsersByMeeting(int meetingId);

    /**
     * <p>Joins user to meeting</p>
     * @param userMeeting contains information about user and meeting
     * @return true if user was successfully joined, false otherwise
     */
    boolean joinUserToMeeting(UserMeeting userMeeting);

    /**
     * <p>Edits user's presence</p>
     * @param userMeeting information about meeting, user and his presence
     * @return true if user's presence was edited successfully, false otherwise
     */
    boolean editUserPresence(UserMeeting userMeeting);
}
