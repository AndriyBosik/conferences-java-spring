package com.conferences.controller;

import com.conferences.entity.User;
import com.conferences.entity.projection.IUserPresence;
import com.conferences.entity.projection.IUserPublicData;
import com.conferences.model.UserPublicData;
import com.conferences.service.abstraction.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
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
        return userService.getJoinedUsersByMeeting(meetingId);
    }
}
