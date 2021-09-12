package com.conferences.service.implementation;

import com.conferences.entity.ReportTopic;
import com.conferences.entity.projection.IMeeting;
import com.conferences.model.Filter;
import com.conferences.model.Sorter;
import com.conferences.repository.IMeetingRepository;
import com.conferences.repository.IReportTopicRepository;
import com.conferences.service.abstraction.IMeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeetingService implements IMeetingService {

    private final IMeetingRepository meetingRepository;
    private final IReportTopicRepository reportTopicRepository;

    @Autowired
    public MeetingService(IMeetingRepository meetingRepository, IReportTopicRepository reportTopicRepository) {
        this.meetingRepository = meetingRepository;
        this.reportTopicRepository = reportTopicRepository;
    }

    @Override
    public Page<IMeeting> getMeetingsByPage(Pageable pageable) {
        Sorter sorter = new Sorter();
        sorter.setColumn("meetings.date");
        Filter filter = new Filter();
        filter.setCondition("1=1");
        return meetingRepository.getWithUsersCountAndTopicsCount(pageable, sorter);
    }

    @Override
    public List<ReportTopic> getMeetingTopics(int meetingId) {
        return reportTopicRepository.findAllByMeetingId(meetingId);
    }
}
