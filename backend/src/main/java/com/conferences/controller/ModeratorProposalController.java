package com.conferences.controller;

import com.conferences.entity.ModeratorProposal;
import com.conferences.service.abstraction.IModeratorProposalService;
import com.conferences.service.implementation.ModeratorProposalService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *     Controller which contains routes to handle moderator proposal requests
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/27
 */
@RestController
@RequestMapping("/api/moderator-proposals")
public class ModeratorProposalController {

    private final IModeratorProposalService moderatorProposalService;

    public ModeratorProposalController(IModeratorProposalService moderatorProposalService) {
        this.moderatorProposalService = moderatorProposalService;
    }

    /**
     * <p>Rejects moderator proposal</p>
     * @param moderatorProposal contains information about proposal
     * @return true if proposal was rejected successfully, false otherwise
     */
    @PostMapping("/reject")
    public boolean rejectModeratorProposal(@RequestBody ModeratorProposal moderatorProposal) {
        moderatorProposalService.rejectProposal(moderatorProposal.getId());
        return true;
    }

    /**
     * <p>Accepts moderator proposal</p>
     * @param moderatorProposal contains information about proposal
     * @return true if proposal was accepted successfully, false otherwise
     */
    @PostMapping("/accept")
    public boolean acceptModeratorProposal(@RequestBody ModeratorProposal moderatorProposal) {
        return moderatorProposalService.acceptProposal(moderatorProposal);
    }

    /**
     * <p>Creates moderator proposal for speaker</p>
     * @param moderatorProposal contains information about proposal
     * @return true if proposal was created successfully, false otherwise
     */
    @PostMapping("/propose")
    public boolean proposeToSpeaker(@RequestBody ModeratorProposal moderatorProposal) {
        moderatorProposalService.create(moderatorProposal);
        return true;
    }
}
