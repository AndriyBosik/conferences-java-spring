package com.conferences.repository;

import com.conferences.entity.ReportTopic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IReportTopicRepository extends JpaRepository<ReportTopic, Integer> {

    List<ReportTopic> findAllByMeetingId(int meetingId);
}
