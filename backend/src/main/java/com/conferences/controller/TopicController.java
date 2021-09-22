package com.conferences.controller;

import com.conferences.entity.ReportTopic;
import com.conferences.entity.TopicProposal;
import com.conferences.entity.User;
import com.conferences.handler.abstraction.IPrivateDataHandler;
import com.conferences.service.abstraction.ITopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/topics")
public class TopicController {

    private final ITopicService topicService;
    private final IPrivateDataHandler<User> userPrivateDataHandler;

    @Autowired
    public TopicController(ITopicService topicService, IPrivateDataHandler<User> userPrivateDataHandler) {
        this.topicService = topicService;
        this.userPrivateDataHandler = userPrivateDataHandler;
    }

    @GetMapping("/get-speaker-proposed-topic-ids")
    public List<Integer> getSpeakerProposedTopicIds(@RequestParam("speakerId") Integer speakerId, @RequestParam("meetingId") Integer meetingId) {
        return topicService.getSpeakerProposedTopicIds(speakerId, meetingId);
    }

    @PostMapping("/create-from-proposal")
    public boolean createFromProposal(@RequestBody TopicProposal topicProposal) {
        return topicService.createFromProposal(topicProposal.getId());
    }

    @PostMapping("/create")
    public boolean createReportTopic(@Valid @RequestBody ReportTopic reportTopic) {
        return topicService.createReportTopic(reportTopic);
    }

    @GetMapping("/get-by-meeting")
    public List<ReportTopic> getByMeeting(@RequestParam int meetingId) {
        List<ReportTopic> reportTopics = topicService.getByMeetingId(meetingId);
        reportTopics.stream()
            .filter(reportTopic -> reportTopic.getReportTopicSpeaker() != null)
            .forEach(reportTopic -> userPrivateDataHandler.clearPrivateData(reportTopic.getReportTopicSpeaker().getSpeaker()));
        return reportTopics;
    }
}
