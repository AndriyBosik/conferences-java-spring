package com.conferences.entity.projection.proposal.topic;

import com.conferences.entity.projection.IMeetingData;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface IMeetingTopicProposalsData extends IMeetingData {

    @Value("#{target.topicProposals}")
    List<ITopicProposal> getTopicProposals();
}
