package com.conferences.service.implementation;

import com.conferences.entity.projection.proposal.IModeratorProposalMeetingData;
import com.conferences.entity.projection.proposal.ISpeakerProposalMeetingData;
import com.conferences.repository.IMeetingRepository;
import com.conferences.service.abstraction.IProposalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProposalsService implements IProposalsService {

    private final IMeetingRepository meetingRepository;

    @Autowired
    public ProposalsService(IMeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    @Override
    public List<ISpeakerProposalMeetingData> getSpeakerProposals(int speakerId) {
        return new ArrayList<>(meetingRepository.getSpeakerProposals(speakerId));
    }

    @Override
    public List<IModeratorProposalMeetingData> getModeratorProposals(int speakerId) {
        return new ArrayList<>(meetingRepository.getModeratorProposals(speakerId));
    }
}
