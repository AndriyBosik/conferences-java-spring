package com.conferences.service.abstraction;

import com.conferences.entity.Meeting;
import com.conferences.entity.ReportTopic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IMeetingService {

    Page<Meeting> getMeetingsByPage(Pageable pageable);

    List<ReportTopic> getMeetingTopics(int meetingId);
}
