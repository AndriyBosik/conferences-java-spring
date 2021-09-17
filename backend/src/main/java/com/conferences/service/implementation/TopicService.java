package com.conferences.service.implementation;

import com.conferences.repository.IReportTopicRepository;
import com.conferences.service.abstraction.ITopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService implements ITopicService {

    private final IReportTopicRepository reportTopicRepository;

    @Autowired
    public TopicService(IReportTopicRepository reportTopicRepository) {
        this.reportTopicRepository = reportTopicRepository;
    }

    @Override
    public List<Integer> getSpeakerProposedTopicIds(int speakerId, int meetingId) {
        return reportTopicRepository.getSpeakerProposedTopicIds(speakerId, meetingId);
    }
}
