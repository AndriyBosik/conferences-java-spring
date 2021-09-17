package com.conferences.repository;

import com.conferences.entity.ReportTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IReportTopicRepository extends JpaRepository<ReportTopic, Integer> {

    List<ReportTopic> findAllByMeetingId(int meetingId);

    @Query(nativeQuery = true, value =
        "SELECT " +
            "sp.report_topic_id " +
        "FROM speaker_proposals sp " +
        "WHERE speaker_id=:speakerId AND EXISTS (" +
            "SELECT NULL FROM report_topics rt WHERE rt.meeting_id=:meetingId AND rt.id=sp.report_topic_id" +
        ")"
    )
    List<Integer> getSpeakerProposedTopicIds(@Param("speakerId") int speakerId, @Param("meetingId") int meetingId);
}
