package com.conferences.service.abstraction;

import com.conferences.entity.User;
import com.conferences.entity.projection.IUserPublicData;
import com.conferences.model.UserPublicData;
import com.conferences.model.UserUpdateData;

import java.util.List;

/**
 * <p>
 *     Defines methods to work with user
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/27
 */
public interface IUserService {

    /**
     * <p>Returns user by login</p>
     * @param login login to extract user by
     * @return extracted instance of {@link User} class
     */
    User getUserByLogin(String login);

    /**
     * <p>Returns all users by role</p>
     * @param role role to extract users by
     * @return list of {@link User} objects
     */
    List<UserPublicData> getUsersByRole(String role);

    /**
     * <p>Returns speakers which are available for some topic</p>
     * @param topicId id of topic
     * @return list of {@link User} objects
     */
    List<UserPublicData> getAvailableSpeakersByTopic(int topicId);

    /**
     * <p>Returns speakers which proposed themselves to some topic</p>
     * @param topicId id of topic
     * @return list of {@link IUserPublicData} objects
     */
    List<IUserPublicData> getProposedSpeakersForTopic(int topicId);

    /**
     * <p>Updates user</p>
     * @param userUpdateData contains updated user's information
     * @return string value which represents new access token. In case of updating failure there will be empty string
     */
    String updateUser(UserUpdateData userUpdateData);

    /**
     * <p>Returns email of authenticated user</p>
     * @return string value which represents email fo authenticated user
     */
    String getUserEmail();

    /**
     * <p>Updates authenticated user's avatar's path</p>
     * @param imagePath new avatar's path
     * @return string value which represents new access token
     */
    String updateUserImagePath(String imagePath);
}
