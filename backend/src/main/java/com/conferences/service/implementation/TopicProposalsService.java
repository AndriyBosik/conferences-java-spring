package com.conferences.service.implementation;

import com.conferences.entity.projection.proposal.topic.IMeetingTopicProposalsData;
import com.conferences.repository.IMeetingRepository;
import com.conferences.repository.ITopicProposalRepository;
import com.conferences.service.abstraction.ITopicProposalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TopicProposalsService implements ITopicProposalsService {

    private final IMeetingRepository meetingRepository;
    private final ITopicProposalRepository topicProposalRepository;

    @Autowired
    public TopicProposalsService(IMeetingRepository meetingRepository, ITopicProposalRepository topicProposalRepository) {
        this.meetingRepository = meetingRepository;
        this.topicProposalRepository = topicProposalRepository;
    }

    @Override
    public List<IMeetingTopicProposalsData> getAllMeetingProposals() {
        return new ArrayList<>(meetingRepository.getMeetingsProposals());
    }

    @Override
    public long getCount() {
        return topicProposalRepository.count();
    }

    @Override
    public void reject(int topicProposalId) {
        topicProposalRepository.deleteById(topicProposalId);
    }
}
