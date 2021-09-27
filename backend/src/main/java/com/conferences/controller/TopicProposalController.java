package com.conferences.controller;

import com.conferences.entity.TopicProposal;
import com.conferences.entity.projection.proposal.topic.IMeetingTopicProposalsData;
import com.conferences.service.abstraction.ITopicProposalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 *     Controller which contains routes to handle topic proposals requests
 * </p>
 */
@RestController
@RequestMapping("/api/topic-proposals")
public class TopicProposalController {

    private final ITopicProposalsService topicProposalsService;

    @Autowired
    public TopicProposalController(ITopicProposalsService topicProposalsService) {
        this.topicProposalsService = topicProposalsService;
    }

    /**
     * <p>Returns all topic proposals</p>
     * @return list of {@link IMeetingTopicProposalsData} objects
     */
    @GetMapping("")
    public List<IMeetingTopicProposalsData> getAllTopicProposals() {
        return topicProposalsService.getAllMeetingProposals();
    }

    /**
     * <p>Returns count of topic proposals</p>
     * @return integer number which represents count of topic proposals
     */
    @GetMapping("/count")
    public long getCount() {
        return topicProposalsService.getCount();
    }

    /**
     * <p>Rejects topic proposal</p>
     * @param topicProposal information about topic proposal to be rejected
     * @return true if proposal was rejected successfully, false otherwise
     */
    @PostMapping("/reject")
    public boolean rejectTopicProposal(@RequestBody TopicProposal topicProposal) {
        topicProposalsService.reject(topicProposal.getId());
        return true;
    }

    /**
     * <p>Creates topic proposal</p>
     * @param topicProposal information about topic proposal to be created
     * @return true if proposal was created successfully, false otherwise
     */
    @PostMapping("/create")
    public boolean createTopicProposal(@Valid @RequestBody TopicProposal topicProposal) {
        return topicProposalsService.create(topicProposal) != null;
    }
}
