package com.conferences.service.implementation;

import com.conferences.entity.projection.proposal.topic.IMeetingData;
import com.conferences.repository.IMeetingRepository;
import com.conferences.service.abstraction.ITopicProposalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TopicProposalsService implements ITopicProposalsService {

    private final IMeetingRepository meetingRepository;

    @Autowired
    public TopicProposalsService(IMeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    @Override
    public List<IMeetingData> getAllMeetingProposals() {
        return new ArrayList<>(meetingRepository.getMeetingsProposals());
    }
}
