package com.conferences.service.implementation;

import com.conferences.entity.ReportTopic;
import com.conferences.entity.ReportTopicSpeaker;
import com.conferences.entity.TopicProposal;
import com.conferences.repository.IReportTopicRepository;
import com.conferences.repository.IReportTopicSpeakerRepository;
import com.conferences.repository.ITopicProposalRepository;
import com.conferences.service.abstraction.ITopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class TopicService implements ITopicService {

    private final IReportTopicRepository reportTopicRepository;
    private final ITopicProposalRepository topicProposalRepository;
    private final IReportTopicSpeakerRepository reportTopicSpeakerRepository;

    @Autowired
    public TopicService(IReportTopicRepository reportTopicRepository, ITopicProposalRepository topicProposalRepository, IReportTopicSpeakerRepository reportTopicSpeakerRepository) {
        this.reportTopicRepository = reportTopicRepository;
        this.topicProposalRepository = topicProposalRepository;
        this.reportTopicSpeakerRepository = reportTopicSpeakerRepository;
    }

    @Override
    public List<Integer> getSpeakerProposedTopicIds(int speakerId, int meetingId) {
        return reportTopicRepository.getSpeakerProposedTopicIds(speakerId, meetingId);
    }

    @Transactional
    @Override
    public boolean createFromProposal(int topicProposalId) {
        reportTopicRepository.createByTopicProposalId(topicProposalId);
        topicProposalRepository.deleteById(topicProposalId);
        return true;
    }

    @Transactional
    @Override
    public boolean createReportTopic(ReportTopic reportTopic) {
        reportTopicRepository.save(reportTopic);
        ReportTopicSpeaker reportTopicSpeaker = reportTopic.getReportTopicSpeaker();
        reportTopic.setReportTopicSpeaker(null);
        if (reportTopicSpeaker != null) {
            reportTopicSpeaker.setReportTopicId(reportTopic.getId());
            reportTopicSpeakerRepository.save(reportTopicSpeaker);
        }
        return true;
    }

    @Override
    public List<ReportTopic> getByMeetingId(int meetingId) {
        return reportTopicRepository.getReportTopicsByMeetingId(meetingId);
    }
}
