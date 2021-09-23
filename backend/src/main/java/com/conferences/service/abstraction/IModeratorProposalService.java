package com.conferences.service.abstraction;

import com.conferences.entity.ModeratorProposal;

public interface IModeratorProposalService {

    void rejectProposal(int moderatorProposalId);

    boolean acceptProposal(ModeratorProposal moderatorProposal);

    void create(ModeratorProposal moderatorProposal);
}
