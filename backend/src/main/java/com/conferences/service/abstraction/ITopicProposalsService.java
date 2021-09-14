package com.conferences.service.abstraction;

import com.conferences.entity.projection.proposal.topic.IMeetingData;

import java.util.List;

public interface ITopicProposalsService {

    List<IMeetingData> getAllMeetingProposals();
}
