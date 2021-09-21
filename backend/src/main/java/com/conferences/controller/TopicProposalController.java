package com.conferences.controller;

import com.conferences.entity.TopicProposal;
import com.conferences.entity.projection.proposal.topic.IMeetingTopicProposalsData;
import com.conferences.service.abstraction.ITopicProposalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public List<IMeetingTopicProposalsData> getAllTopicProposals() {
        return topicProposalsService.getAllMeetingProposals();
    }

    @GetMapping("/count")
    public long getCount() {
        return topicProposalsService.getCount();
    }

    @PostMapping("/reject")
    public boolean rejectTopicProposal(@RequestBody TopicProposal topicProposal) {
        topicProposalsService.reject(topicProposal.getId());
        return true;
    }

    @PostMapping("/create")
    public boolean createTopicProposal(@Valid @RequestBody TopicProposal topicProposal) {
        return topicProposalsService.create(topicProposal) != null;
    }
}
