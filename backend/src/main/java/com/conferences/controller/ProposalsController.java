package com.conferences.controller;

import com.conferences.entity.projection.proposal.IModeratorProposalMeetingData;
import com.conferences.entity.projection.proposal.ISpeakerProposalMeetingData;
import com.conferences.service.abstraction.IProposalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proposals")
public class ProposalsController {

    private final IProposalsService proposalsService;

    @Autowired
    public ProposalsController(IProposalsService proposalsService) {
        this.proposalsService = proposalsService;
    }

    @GetMapping("/speaker/{speakerId}")
    public List<ISpeakerProposalMeetingData> getSpeakerProposals(@PathVariable int speakerId) {
        return proposalsService.getSpeakerProposals(speakerId);
    }

    @GetMapping("/moderator/{speakerId}")
    public List<IModeratorProposalMeetingData> getModeratorProposals(@PathVariable int speakerId) {
        return proposalsService.getModeratorProposals(speakerId);
    }
}
