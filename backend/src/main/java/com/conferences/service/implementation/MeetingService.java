package com.conferences.service.implementation;

import com.conferences.entity.Meeting;
import com.conferences.entity.ReportTopic;
import com.conferences.entity.User;
import com.conferences.entity.UserMeeting;
import com.conferences.entity.projection.IMeetingWithStats;
import com.conferences.handler.abstraction.IPrivateDataHandler;
import com.conferences.model.DateFilter;
import com.conferences.model.MeetingData;
import com.conferences.model.MeetingUpdatableData;
import com.conferences.repository.IMeetingRepository;
import com.conferences.repository.IReportTopicRepository;
import com.conferences.repository.IUserMeetingRepository;
import com.conferences.service.abstraction.IMeetingService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log4j2
@Service
public class MeetingService implements IMeetingService {

    private final IMeetingRepository meetingRepository;
    private final IUserMeetingRepository userMeetingRepository;
    private final IReportTopicRepository reportTopicRepository;
    private final IPrivateDataHandler<User> userPrivateDataHandler;

    @Autowired
    public MeetingService(IMeetingRepository meetingRepository, IUserMeetingRepository userMeetingRepository, IReportTopicRepository reportTopicRepository, IPrivateDataHandler<User> userPrivateDataHandler) {
        this.meetingRepository = meetingRepository;
        this.userMeetingRepository = userMeetingRepository;
        this.reportTopicRepository = reportTopicRepository;
        this.userPrivateDataHandler = userPrivateDataHandler;
    }

    @Override
    public Page<IMeetingWithStats> getMeetingsByPage(Pageable pageable, DateFilter dateFilter) {
        log.info("Getting {} meetings for {} page", pageable.getPageSize(), pageable.getPageNumber() + 1);
        return meetingRepository.findAllWithFilters(pageable, dateFilter);
    }

    @Override
    public Page<IMeetingWithStats> getMeetingsByPageAndSpeaker(Pageable pageable, DateFilter dateFilter, Integer speakerId) {
        log.info("Getting {} speaker meetings for {} page", pageable.getPageSize(), pageable.getPageNumber() + 1);
        return meetingRepository.findAllWithFiltersBySpeaker(pageable, dateFilter, speakerId);
    }

    @Override
    public MeetingData getMeeting(int meetingId) {
        log.info("Getting meeting");
        Meeting meeting = meetingRepository.findAllById(meetingId);
        meeting.getReportTopics().stream()
            .filter(reportTopic -> reportTopic.getReportTopicSpeaker() != null)
            .forEach(reportTopic -> userPrivateDataHandler.clearPrivateData(reportTopic.getReportTopicSpeaker().getSpeaker()));
        return new MeetingData(
            meeting,
            userMeetingRepository.countAllByMeetingId(meetingId)
        );
    }

    @Override
    public List<ReportTopic> getMeetingTopics(int meetingId) {
        log.info("Getting meeting's report topics");
        return reportTopicRepository.findAllByMeetingId(meetingId);
    }

    @Override
    public boolean isUserJoined(UserMeeting userMeeting) {
        log.info("Checking if user is joined to meeting");
        return userMeetingRepository.findByUserIdAndMeetingId(userMeeting.getUserId(), userMeeting.getMeetingId()) != null;
    }

    @Override
    public boolean createMeeting(Meeting meeting) {
        log.info("Saving meeting");
        meetingRepository.save(meeting);
        return true;
    }

    @Transactional
    @Override
    public boolean editMeeting(MeetingUpdatableData meeting) {
        log.info("Editing meeting");
        meetingRepository.editMeeting(meeting);
        return true;
    }
}
