package com.conferences.service.abstraction;

import com.conferences.entity.ReportTopic;
import com.conferences.entity.projection.IMeeting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IMeetingService {

    Page<IMeeting> getMeetingsByPage(Pageable pageable);

    List<ReportTopic> getMeetingTopics(int meetingId);
}
