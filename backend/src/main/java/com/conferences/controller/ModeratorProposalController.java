package com.conferences.controller;

import com.conferences.entity.ModeratorProposal;
import com.conferences.service.abstraction.IModeratorProposalService;
import com.conferences.service.implementation.ModeratorProposalService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/moderator-proposals")
public class ModeratorProposalController {

    private final IModeratorProposalService moderatorProposalService;

    public ModeratorProposalController(IModeratorProposalService moderatorProposalService) {
        this.moderatorProposalService = moderatorProposalService;
    }

    @PostMapping("/reject")
    public boolean rejectModeratorProposal(@RequestBody ModeratorProposal moderatorProposal) {
        moderatorProposalService.rejectProposal(moderatorProposal.getId());
        return true;
    }

    @PostMapping("/accept")
    public boolean acceptModeratorProposal(@RequestBody ModeratorProposal moderatorProposal) {
        return moderatorProposalService.acceptProposal(moderatorProposal);
    }

    @PostMapping("/propose")
    public boolean proposeToSpeaker(@RequestBody ModeratorProposal moderatorProposal) {
        moderatorProposalService.create(moderatorProposal);
        return true;
    }
}
