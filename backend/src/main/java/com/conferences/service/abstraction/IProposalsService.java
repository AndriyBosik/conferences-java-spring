package com.conferences.service.abstraction;

import com.conferences.entity.projection.proposal.moderator.IModeratorProposalMeetingData;
import com.conferences.entity.projection.proposal.speaker.ISpeakerProposalMeetingData;

import java.util.List;

/**
 * <p>
 *     Defines methods to work with proposals for topics
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/27
 */
public interface IProposalsService {

    /**
     * <p>Extracts topics for which speaker proposed himself</p>
     * @param speakerId id of speaker
     * @return set of {@link ISpeakerProposalMeetingData}
     */
    List<ISpeakerProposalMeetingData> getSpeakerProposals(int speakerId);

    /**
     * <p>Returns moderator proposed topics for speaker</p>
     * @param speakerId id of speaker
     * @return list of {@link IModeratorProposalMeetingData}
     */
    List<IModeratorProposalMeetingData> getModeratorProposals(int speakerId);
}
