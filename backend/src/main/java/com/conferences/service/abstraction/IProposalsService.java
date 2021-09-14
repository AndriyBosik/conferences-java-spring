package com.conferences.service.abstraction;

import com.conferences.entity.projection.proposal.IModeratorProposalMeetingData;
import com.conferences.entity.projection.proposal.ISpeakerProposalMeetingData;

import java.util.List;

public interface IProposalsService {

    List<ISpeakerProposalMeetingData> getSpeakerProposals(int speakerId);

    List<IModeratorProposalMeetingData> getModeratorProposals(int speakerId);
}
