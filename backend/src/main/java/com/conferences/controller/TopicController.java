package com.conferences.controller;

import com.conferences.service.abstraction.ITopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/topics")
public class TopicController {

    private final ITopicService topicService;

    @Autowired
    public TopicController(ITopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping("/get-speaker-proposed-topic-ids")
    public List<Integer> getSpeakerProposedTopicIds(@RequestParam("speakerId") Integer speakerId, @RequestParam("meetingId") Integer meetingId) {
        return topicService.getSpeakerProposedTopicIds(speakerId, meetingId);
    }
}
