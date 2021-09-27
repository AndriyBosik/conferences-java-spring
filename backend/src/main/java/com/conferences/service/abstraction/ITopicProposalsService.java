package com.conferences.service.abstraction;

import com.conferences.entity.TopicProposal;
import com.conferences.entity.projection.proposal.speaker.ISpeakerProposalMeetingData;
import com.conferences.entity.projection.proposal.topic.IMeetingTopicProposalsData;

import java.util.List;

/**
 * <p>
 *     Defines methods to work with topic proposals
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/27
 */
public interface ITopicProposalsService {

    /**
     * <p>Extracts all meetings with their topic proposals</p>
     * @return list of {@link IMeetingTopicProposalsData}
     */
    List<IMeetingTopicProposalsData> getAllMeetingProposals();

    /**
     * <p>Returns count of topic proposals</p>
     * @return long value which represents count of topic proposals
     */
    long getCount();

    /**
     * <p>Rejects topic proposal</p>
     * @param topicProposalId id of topic proposal
     */
    void reject(int topicProposalId);

    /**
     * <p>Creates topic proposal</p>
     * @param topicProposal contains information about topic proposal
     * @return created {@link TopicProposal} object of null if there were some error during creation
     */
    TopicProposal create(TopicProposal topicProposal);
}
