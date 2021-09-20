package com.conferences.service.abstraction;

import com.conferences.entity.TopicProposal;

import java.util.List;

public interface ITopicService {

    List<Integer> getSpeakerProposedTopicIds(int speakerId, int meetingId);

    boolean createFromProposal(int topicProposalId);
}
