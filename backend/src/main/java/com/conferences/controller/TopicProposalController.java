package com.conferences.controller;

import com.conferences.entity.projection.topic.proposal.IMeetingData;
import com.conferences.service.abstraction.ITopicProposalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/topic-proposals")
public class TopicProposalController {

    private final ITopicProposalsService topicProposalsService;

    @Autowired
    public TopicProposalController(ITopicProposalsService topicProposalsService) {
        this.topicProposalsService = topicProposalsService;
    }

    @GetMapping("")
    public List<IMeetingData> getAllTopicProposals() {
        return topicProposalsService.getAllMeetingProposals();
    }
}
