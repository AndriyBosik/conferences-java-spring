package com.conferences.entity.projection.proposal.moderator;

import com.conferences.entity.projection.IReportTopicData;
import com.conferences.entity.projection.proposal.IProposal;
import org.springframework.beans.factory.annotation.Value;

import java.util.Set;

public interface IModeratorReportTopic extends IReportTopicData {

    @Value("#{target.moderatorProposals}")
    Set<IProposal> getModeratorProposals();
}
