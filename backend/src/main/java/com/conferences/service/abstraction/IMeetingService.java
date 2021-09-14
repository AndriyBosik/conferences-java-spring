package com.conferences.service.abstraction;

import com.conferences.entity.Meeting;
import com.conferences.entity.ReportTopic;
import com.conferences.entity.projection.IMeetingWithStats;
import com.conferences.model.DateFilter;
import com.conferences.model.MeetingData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IMeetingService {

    Page<IMeetingWithStats> getMeetingsByPage(Pageable pageable, DateFilter dateFilter);

    MeetingData getMeeting(int meetingId);

    List<ReportTopic> getMeetingTopics(int meetingId);
}
