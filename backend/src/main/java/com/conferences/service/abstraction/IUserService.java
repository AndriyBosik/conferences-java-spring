package com.conferences.service.abstraction;

import com.conferences.entity.User;
import com.conferences.entity.projection.IUserPresence;
import com.conferences.entity.projection.IUserPublicData;
import com.conferences.model.UserData;

import java.util.List;

public interface IUserService {

    User getUserByLogin(String login);

    List<UserData> getUsersByRole(String role);

    List<UserData> getAvailableSpeakersByTopic(int topicId);

    List<IUserPublicData> getProposedSpeakersForTopic(int topicId);

    List<IUserPresence> getJoinedUsersByMeeting(int meetingId);
}
