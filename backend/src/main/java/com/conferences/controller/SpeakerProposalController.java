package com.conferences.controller;

import com.conferences.entity.SpeakerProposal;
import com.conferences.service.abstraction.ISpeakerProposalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/speaker-proposals")
public class SpeakerProposalController {

    private final ISpeakerProposalService speakerProposalService;

    @Autowired
    public SpeakerProposalController(ISpeakerProposalService speakerProposalService) {
        this.speakerProposalService = speakerProposalService;
    }

    @PostMapping("/propose")
    public boolean proposeSpeaker(@RequestBody SpeakerProposal speakerProposal) {
        return speakerProposalService.proposeSpeaker(speakerProposal);
    }
}
