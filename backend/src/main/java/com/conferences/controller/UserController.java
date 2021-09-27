package com.conferences.controller;

import com.conferences.entity.User;
import com.conferences.entity.UserMeeting;
import com.conferences.entity.projection.IUserPresence;
import com.conferences.entity.projection.IUserPublicData;
import com.conferences.model.UserPublicData;
import com.conferences.model.UserUpdateData;
import com.conferences.service.abstraction.IUserMeetingService;
import com.conferences.service.abstraction.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 *     Controller which contains routes to handle user request
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/27
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final IUserService userService;
    private final IUserMeetingService userMeetingService;

    @Autowired
    public UserController(IUserService userService, IUserMeetingService userMeetingService) {
        this.userService = userService;
        this.userMeetingService = userMeetingService;
    }

    /**
     * <p>Returns user by login</p>
     * @param login user's login
     * @return an instance of {@link User} class or null if user was not found
     */
    @GetMapping("/{login}")
    public User getUserByLogin(@PathVariable String login) {
        return userService.getUserByLogin(login);
    }

    /**
     * <p>Returns speakers</p>
     * @return list of {@link User} objects
     */
    @GetMapping("/speakers")
    public List<UserPublicData> getSpeakers() {
        return userService.getUsersByRole("speaker");
    }

    /**
     * <p>Returns speakers which are available for some topic</p>
     * @param topicId id of topic
     * @return list of {@link UserPublicData} objects
     */
    @GetMapping("/get-topic-available")
    public List<UserPublicData> getTopicAvailable(Integer topicId) {
        return userService.getAvailableSpeakersByTopic(topicId);
    }

    /**
     * <p>Returns speakers which proposed themselves to some topic</p>
     * @param topicId id of topic
     * @return list of {@link IUserPublicData} objects
     */
    @GetMapping("/get-topic-proposed")
    public List<IUserPublicData> getTopicProposed(Integer topicId) {
        return userService.getProposedSpeakersForTopic(topicId);
    }

    /**
     * <p>Returns users with their presence for some meeting</p>
     * @param meetingId id of meeting
     * @return list of {@link IUserPresence} objects
     */
    @GetMapping("/get-for-meeting")
    public List<IUserPresence> getForMeeting(int meetingId) {
        return userMeetingService.getJoinedUsersByMeeting(meetingId);
    }

    /**
     * <p>Updates user profile</p>
     * @param userUpdateData new information needed to be updated
     * @return new access token
     */
    @PostMapping("/update-profile")
    public String updateProfile(@Valid @RequestBody UserUpdateData userUpdateData) {
        return userService.updateUser(userUpdateData);
    }

    /**
     * <p>Joins user to meeting</p>
     * @param userMeeting information about user and meeting
     * @return true if user was successfully joined to meeting, false otherwise
     */
    @PostMapping("/join-to-meeting")
    public boolean joinToMeeting(@RequestBody UserMeeting userMeeting) {
        return userMeetingService.joinUserToMeeting(userMeeting);
    }

    /**
     * <p>Edits user's presence</p>
     * @param userMeeting information about meeting, user and his presence
     * @return true if user's presence was edited successfully, false otherwise
     */
    @PostMapping("/edit-presence")
    public boolean editPresence(@RequestBody UserMeeting userMeeting) {
        return userMeetingService.editUserPresence(userMeeting);
    }

    /**
     * <p>Returns email of authorized user</p>
     * @return string which represents user's email
     */
    @GetMapping("/get-email")
    public String getUserEmail() {
        return userService.getUserEmail();
    }
}
