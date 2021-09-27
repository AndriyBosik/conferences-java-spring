package com.conferences.controller;

import com.conferences.entity.projection.proposal.moderator.IModeratorProposalMeetingData;
import com.conferences.entity.projection.proposal.speaker.ISpeakerProposalMeetingData;
import com.conferences.service.abstraction.IProposalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *     Controller which contains routes to handle proposals requests
 * </p>
 */
@RestController
@RequestMapping("/api/proposals")
public class ProposalsController {

    private final IProposalsService proposalsService;

    @Autowired
    public ProposalsController(IProposalsService proposalsService) {
        this.proposalsService = proposalsService;
    }

    /**
     * <p>Returns meetings with their topics for which speaker proposed himself</p>
     * @param speakerId if of speaker
     * @return list of {@link ISpeakerProposalMeetingData} objects
     */
    @GetMapping("/speaker/{speakerId}")
    public List<ISpeakerProposalMeetingData> getSpeakerProposals(@PathVariable int speakerId) {
        return proposalsService.getSpeakerProposals(speakerId);
    }

    /**
     * <p>Returns meetings with their moderator proposed topics for speaker</p>
     * @param speakerId if of speaker
     * @return list of {@link IModeratorProposalMeetingData} objects
     */
    @GetMapping("/moderator/{speakerId}")
    public List<IModeratorProposalMeetingData> getModeratorProposals(@PathVariable int speakerId) {
        return proposalsService.getModeratorProposals(speakerId);
    }
}
