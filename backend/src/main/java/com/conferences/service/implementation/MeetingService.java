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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        return meetingRepository.findAllWithFilters(pageable, dateFilter);
    }

    @Override
    public Page<IMeetingWithStats> getMeetingsByPageAndSpeaker(Pageable pageable, DateFilter dateFilter, Integer speakerId) {
        return meetingRepository.findAllWithFiltersBySpeaker(pageable, dateFilter, speakerId);
    }

    @Override
    public MeetingData getMeeting(int meetingId) {
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
        return reportTopicRepository.findAllByMeetingId(meetingId);
    }

    @Override
    public boolean isUserJoined(UserMeeting userMeeting) {
        return userMeetingRepository.findByUserIdAndMeetingId(userMeeting.getUserId(), userMeeting.getMeetingId()) != null;
    }

    @Override
    public boolean createMeeting(Meeting meeting) {
        meetingRepository.save(meeting);
        return true;
    }

    @Transactional
    @Override
    public boolean editMeeting(MeetingUpdatableData meeting) {
        meetingRepository.editMeeting(meeting);
        return true;
    }
}
