package com.conferences.entity.projection.proposal.speaker;

import com.conferences.entity.projection.IReportTopicData;
import com.conferences.entity.projection.proposal.IProposal;
import org.springframework.beans.factory.annotation.Value;

import java.util.Set;

public interface ISpeakerReportTopic extends IReportTopicData {

    @Value("#{target.speakerProposals}")
    Set<IProposal> getSpeakerProposals();
}
