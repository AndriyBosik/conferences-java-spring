package com.conferences.service.abstraction;

import com.conferences.entity.User;
import com.conferences.entity.projection.IUserPublicData;
import com.conferences.model.UserPublicData;
import com.conferences.model.UserUpdateData;

import java.util.List;

public interface IUserService {

    User getUserByLogin(String login);

    List<UserPublicData> getUsersByRole(String role);

    List<UserPublicData> getAvailableSpeakersByTopic(int topicId);

    List<IUserPublicData> getProposedSpeakersForTopic(int topicId);

    String updateUser(UserUpdateData userUpdateData);
}
