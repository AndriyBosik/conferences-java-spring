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

    @Autowired
    public TopicController(ITopicService topicService) {
        this.topicService = topicService;
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

    @PostMapping("/edit")
    public boolean editReportTopic(@Valid @RequestBody ReportTopic reportTopic) {
        return topicService.editReportTopic(reportTopic);
    }

    @GetMapping("/get-by-meeting")
    public List<ReportTopic> getByMeeting(@RequestParam int meetingId) {
        return topicService.getByMeetingId(meetingId);
    }
}
