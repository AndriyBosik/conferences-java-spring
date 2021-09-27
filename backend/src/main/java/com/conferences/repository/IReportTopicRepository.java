package com.conferences.repository;

import com.conferences.entity.ReportTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * <p>
 *     Defines methods to work with report_topics in database
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/27
 */
public interface IReportTopicRepository extends JpaRepository<ReportTopic, Integer> {

    /**
     * <p>Extracts all report topics will all of their data for meeting</p>
     * @param meetingId id of meeting
     * @return list of {@link ReportTopic}
     */
    List<ReportTopic> findAllByMeetingId(int meetingId);

    /**
     * <p>Extracts IDs of topics for which speaker proposed himself</p>
     * @param speakerId id of speaker
     * @param meetingId id of meeting
     * @return list of IDs
     */
    @Query(nativeQuery = true, value =
        "SELECT " +
            "sp.report_topic_id " +
        "FROM speaker_proposals sp " +
        "WHERE speaker_id=:speakerId AND EXISTS (" +
            "SELECT NULL FROM report_topics rt WHERE rt.meeting_id=:meetingId AND rt.id=sp.report_topic_id" +
        ")"
    )
    List<Integer> getSpeakerProposedTopicIds(@Param("speakerId") int speakerId, @Param("meetingId") int meetingId);

    /**
     * <p>Creates report topic by topic proposal</p>
     * @param topicProposalId id of topic proposal to create report topic from
     * @return number of created records
     */
    @Modifying
    @Query(nativeQuery = true, value =
        "INSERT INTO report_topics(title, meeting_id) " +
            "SELECT tp.topic_title, tp.meeting_id FROM topic_proposals tp WHERE id=:topicProposalId"
    )
    int createByTopicProposalId(@Param("topicProposalId") int topicProposalId);

    /**
     * <p>Extracts report topics for meeting with speakers</p>
     * @param meetingId id of meeting
     * @return list of {@link ReportTopic}
     */
    @Query(value =
        "SELECT rt FROM ReportTopic rt " +
            "LEFT JOIN FETCH rt.reportTopicSpeaker AS rts " +
                "LEFT JOIN FETCH rts.speaker AS speaker WHERE rt.meetingId=:meetingId"
    )
    List<ReportTopic> getReportTopicsByMeetingId(@Param("meetingId") int meetingId);
}
