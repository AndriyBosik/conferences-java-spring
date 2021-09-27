package com.conferences.service.implementation;

import com.conferences.entity.TopicProposal;
import com.conferences.entity.projection.proposal.topic.IMeetingTopicProposalsData;
import com.conferences.repository.IMeetingRepository;
import com.conferences.repository.ITopicProposalRepository;
import com.conferences.service.abstraction.ITopicProposalsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * {@inheritDoc}
 */
@Log4j2
@Service
public class TopicProposalsService implements ITopicProposalsService {

    private final IMeetingRepository meetingRepository;
    private final ITopicProposalRepository topicProposalRepository;

    @Autowired
    public TopicProposalsService(IMeetingRepository meetingRepository, ITopicProposalRepository topicProposalRepository) {
        this.meetingRepository = meetingRepository;
        this.topicProposalRepository = topicProposalRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IMeetingTopicProposalsData> getAllMeetingProposals() {
        log.info("Getting topic proposals for each meeting");
        return new ArrayList<>(meetingRepository.getMeetingsProposals());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getCount() {
        log.info("Getting count of topic proposals");
        return topicProposalRepository.count();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reject(int topicProposalId) {
        log.info("Deleting topic proposal");
        topicProposalRepository.deleteById(topicProposalId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TopicProposal create(TopicProposal topicProposal) {
        log.info("Creating topic proposal");
        return topicProposalRepository.save(topicProposal);
    }
}
