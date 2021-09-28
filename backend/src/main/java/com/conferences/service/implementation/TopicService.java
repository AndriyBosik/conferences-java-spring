package com.conferences.service.implementation;

import com.conferences.entity.ReportTopic;
import com.conferences.entity.ReportTopicSpeaker;
import com.conferences.entity.TopicProposal;
import com.conferences.entity.User;
import com.conferences.handler.abstraction.IPrivateDataHandler;
import com.conferences.repository.IReportTopicRepository;
import com.conferences.repository.IReportTopicSpeakerRepository;
import com.conferences.repository.ITopicProposalRepository;
import com.conferences.service.abstraction.ITopicService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * {@inheritDoc}
 */
@Log4j2
@Service
public class TopicService implements ITopicService {

    private final IReportTopicRepository reportTopicRepository;
    private final ITopicProposalRepository topicProposalRepository;
    private final IReportTopicSpeakerRepository reportTopicSpeakerRepository;
    private final IPrivateDataHandler<User> userPrivateDataHandler;

    @Autowired
    public TopicService(IReportTopicRepository reportTopicRepository, ITopicProposalRepository topicProposalRepository, IReportTopicSpeakerRepository reportTopicSpeakerRepository, IPrivateDataHandler<User> userPrivateDataHandler) {
        this.reportTopicRepository = reportTopicRepository;
        this.topicProposalRepository = topicProposalRepository;
        this.reportTopicSpeakerRepository = reportTopicSpeakerRepository;
        this.userPrivateDataHandler = userPrivateDataHandler;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Integer> getSpeakerProposedTopicIds(int speakerId, int meetingId) {
        log.info("Getting ids for speaker proposed topics");
        return reportTopicRepository.getSpeakerProposedTopicIds(speakerId, meetingId);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public boolean createFromProposal(int topicProposalId) {
        log.info("Creating report topic from topic proposal");
        reportTopicRepository.createByTopicProposalId(topicProposalId);
        log.info("Deleting topic proposal");
        topicProposalRepository.deleteById(topicProposalId);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public boolean createReportTopic(ReportTopic reportTopic) {
        log.info("Creating report topic");
        ReportTopicSpeaker reportTopicSpeaker = reportTopic.getReportTopicSpeaker();
        reportTopic.setReportTopicSpeaker(null);
        reportTopic = reportTopicRepository.save(reportTopic);
        if (reportTopicSpeaker != null) {
            reportTopicSpeaker.setReportTopicId(reportTopic.getId());
            reportTopicSpeakerRepository.save(reportTopicSpeaker);
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public boolean editReportTopic(ReportTopic reportTopic) {
        if (reportTopic.getId() == null || reportTopic.getId() == 0) {
            return false;
        }
        log.info("Edition report topic");
        ReportTopicSpeaker reportTopicSpeaker = reportTopic.getReportTopicSpeaker();
        reportTopic.setReportTopicSpeaker(null);
        reportTopic.setModeratorProposals(null);
        System.out.println("id: " + reportTopic.getId());
        reportTopic = reportTopicRepository.save(reportTopic);
        reportTopicSpeakerRepository.deleteByReportTopicId(reportTopic.getId());
        if (reportTopicSpeaker != null) {
            reportTopicSpeaker.setReportTopicId(reportTopic.getId());
            reportTopicSpeakerRepository.save(reportTopicSpeaker);
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ReportTopic> getByMeetingId(int meetingId) {
        log.info("Getting report topics by meeting");
        List<ReportTopic> reportTopics = reportTopicRepository.getReportTopicsByMeetingId(meetingId);
        reportTopics.stream()
                .filter(reportTopic -> reportTopic.getReportTopicSpeaker() != null)
                .forEach(reportTopic -> userPrivateDataHandler.clearPrivateData(reportTopic.getReportTopicSpeaker().getSpeaker()));
        return reportTopics;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSpeaker(ReportTopicSpeaker reportTopicSpeaker) {
        log.info("Setting speaker for report topic");
        reportTopicSpeaker.setId(0);
        reportTopicSpeakerRepository.save(reportTopicSpeaker);
    }
}
