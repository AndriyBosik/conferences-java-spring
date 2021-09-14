package com.conferences.service.abstraction;

import com.conferences.entity.projection.proposal.moderator.IModeratorProposalMeetingData;
import com.conferences.entity.projection.proposal.speaker.ISpeakerProposalMeetingData;

import java.util.List;

public interface IProposalsService {

    List<ISpeakerProposalMeetingData> getSpeakerProposals(int speakerId);

    List<IModeratorProposalMeetingData> getModeratorProposals(int speakerId);
}
