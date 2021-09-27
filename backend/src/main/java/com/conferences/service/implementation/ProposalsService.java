package com.conferences.service.implementation;

import com.conferences.entity.projection.proposal.moderator.IModeratorProposalMeetingData;
import com.conferences.entity.projection.proposal.speaker.ISpeakerProposalMeetingData;
import com.conferences.repository.IMeetingRepository;
import com.conferences.service.abstraction.IProposalsService;
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
public class ProposalsService implements IProposalsService {

    private final IMeetingRepository meetingRepository;

    @Autowired
    public ProposalsService(IMeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ISpeakerProposalMeetingData> getSpeakerProposals(int speakerId) {
        log.info("Getting speaker proposals by speakerId");
        return new ArrayList<>(meetingRepository.getSpeakerProposals(speakerId));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IModeratorProposalMeetingData> getModeratorProposals(int speakerId) {
        log.info("Getting moderator propoposals by speakerId");
        return new ArrayList<>(meetingRepository.getModeratorProposals(speakerId));
    }
}
