package com.conferences.controller;

import com.conferences.entity.User;
import com.conferences.entity.UserMeeting;
import com.conferences.entity.projection.IUserPresence;
import com.conferences.entity.projection.IUserPublicData;
import com.conferences.model.UserPublicData;
import com.conferences.model.UserRegistrationData;
import com.conferences.model.UserUpdateData;
import com.conferences.service.abstraction.IUserMeetingService;
import com.conferences.service.abstraction.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;

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

    @GetMapping("/{login}")
    public User getUserById(@PathVariable String login) {
        return userService.getUserByLogin(login);
    }

    @GetMapping("/speakers")
    public List<UserPublicData> getSpeakers() {
        return userService.getUsersByRole("speaker");
    }

    @GetMapping("/get-topic-available")
    public List<UserPublicData> getTopicAvailable(Integer topicId) {
        return userService.getAvailableSpeakersByTopic(topicId);
    }

    @GetMapping("/get-topic-proposed")
    public List<IUserPublicData> getTopicProposed(Integer topicId) {
        return userService.getProposedSpeakersForTopic(topicId);
    }

    @GetMapping("/get-for-meeting")
    public List<IUserPresence> getForMeeting(int meetingId) {
        return userMeetingService.getJoinedUsersByMeeting(meetingId);
    }

    @PostMapping("/update-profile")
    public String updateProfile(@Valid @RequestBody UserUpdateData userUpdateData) {
        return userService.updateUser(userUpdateData);
    }

    @PostMapping("/join-to-meeting")
    public boolean joinToMeeting(@RequestBody UserMeeting userMeeting) {
        return userMeetingService.joinUserToMeeting(userMeeting);
    }
}
