package com.conferences.service.implementation;

import com.conferences.entity.Meeting;
import com.conferences.entity.ReportTopic;
import com.conferences.entity.UserMeeting;
import com.conferences.entity.projection.IMeetingWithStats;
import com.conferences.model.DateFilter;
import com.conferences.model.MeetingData;
import com.conferences.repository.IMeetingRepository;
import com.conferences.repository.IReportTopicRepository;
import com.conferences.repository.IUserMeetingRepository;
import com.conferences.service.abstraction.IMeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeetingService implements IMeetingService {

    private final IMeetingRepository meetingRepository;
    private final IUserMeetingRepository userMeetingRepository;
    private final IReportTopicRepository reportTopicRepository;

    @Autowired
    public MeetingService(IMeetingRepository meetingRepository, IUserMeetingRepository userMeetingRepository, IReportTopicRepository reportTopicRepository) {
        this.meetingRepository = meetingRepository;
        this.userMeetingRepository = userMeetingRepository;
        this.reportTopicRepository = reportTopicRepository;
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
        return new MeetingData(
            meetingRepository.findAllById(meetingId),
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
}
