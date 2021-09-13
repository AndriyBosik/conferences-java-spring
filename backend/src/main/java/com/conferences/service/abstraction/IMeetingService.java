package com.conferences.service.abstraction;

import com.conferences.entity.Meeting;
import com.conferences.entity.ReportTopic;
import com.conferences.entity.projection.IMeeting;
import com.conferences.model.DateFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IMeetingService {

    Page<IMeeting> getMeetingsByPage(Pageable pageable, DateFilter dateFilter);

    List<ReportTopic> getMeetingTopics(int meetingId);
}
