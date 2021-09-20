package com.conferences.service.abstraction;

import com.conferences.entity.projection.proposal.topic.IMeetingTopicProposalsData;

import java.util.List;

public interface ITopicProposalsService {

    List<IMeetingTopicProposalsData> getAllMeetingProposals();

    long getCount();

    void reject(int topicProposalId);
}
