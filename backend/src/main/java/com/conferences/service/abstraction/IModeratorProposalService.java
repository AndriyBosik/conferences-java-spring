package com.conferences.service.abstraction;

import com.conferences.entity.ModeratorProposal;

/**
 * <p>
 *     Defines methods to work with moderator proposals
 * </p>
 */
public interface IModeratorProposalService {

    /**
     * <p>Rejects moderator proposals</p>
     * @param moderatorProposalId id of moderator proposal
     */
    void rejectProposal(int moderatorProposalId);

    /**
     * <p>Accepts moderator proposal</p>
     * @param moderatorProposal contains information about moderator proposal
     * @return true if proposal was successfully accepted, false otherwise
     */
    boolean acceptProposal(ModeratorProposal moderatorProposal);

    /**
     * <p>Creates moderator proposal</p>
     * @param moderatorProposal contains information about new moderator proposal
     */
    void create(ModeratorProposal moderatorProposal);
}
