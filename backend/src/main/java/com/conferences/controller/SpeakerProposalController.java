package com.conferences.controller;

import com.conferences.entity.SpeakerProposal;
import com.conferences.service.abstraction.ISpeakerProposalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *     Controller which contains router to handle speake proposals requests
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/27
 */
@RestController
@RequestMapping("/api/speaker-proposals")
public class SpeakerProposalController {

    private final ISpeakerProposalService speakerProposalService;

    @Autowired
    public SpeakerProposalController(ISpeakerProposalService speakerProposalService) {
        this.speakerProposalService = speakerProposalService;
    }

    /**
     * <p>Creates speaker proposal</p>
     * @param speakerProposal information about speaker proposal
     * @return true if proposal was created successfully, false otherwise
     */
    @PostMapping("/propose")
    public boolean proposeSpeaker(@RequestBody SpeakerProposal speakerProposal) {
        return speakerProposalService.proposeSpeaker(speakerProposal);
    }
}
