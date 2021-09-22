package com.conferences.repository;

import com.conferences.entity.Meeting;
import com.conferences.entity.projection.IMeetingWithStats;
import com.conferences.entity.projection.proposal.moderator.IModeratorProposalMeetingData;
import com.conferences.entity.projection.proposal.speaker.ISpeakerProposalMeetingData;
import com.conferences.entity.projection.proposal.topic.IMeetingTopicProposalsData;
import com.conferences.model.DateFilter;
import com.conferences.model.MeetingUpdatableData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface IMeetingRepository extends PagingAndSortingRepository<Meeting, Integer> {

    @Query(nativeQuery = true, value =
        "SELECT " +
            "meetings.*," +
            "COALESCE(stats.users_count, 0) AS users_count," +
            "COALESCE(stats.present_users_count, 0) AS present_users_count," +
            "(SELECT COUNT(id) FROM report_topics WHERE meeting_id=meetings.id) AS topics_count " +
        "FROM meetings " +
            "LEFT JOIN (" +
                "SELECT " +
                    "meeting_id," +
                    "COUNT(id) AS users_count, " +
                    "SUM(" +
                        "CASE WHEN present THEN 1 END" +
                    ") AS present_users_count " +
                "FROM users_meetings GROUP BY meeting_id " +
            ") AS stats ON stats.meeting_id=meetings.id " +
        "WHERE meetings.date BETWEEN :#{#dateFilter.getMin()} AND :#{#dateFilter.getMax()} " +
        "GROUP BY meetings.id, stats.users_count, stats.present_users_count",
        countQuery = "SELECT COUNT(id) FROM meetings WHERE date BETWEEN :#{#dateFilter.getMin()} AND :#{#dateFilter.getMax()}"
    )
    Page<IMeetingWithStats> findAllWithFilters(Pageable pageable, @Param("dateFilter") DateFilter dateFilter);

    @Query(nativeQuery = true, value =
            "SELECT " +
                "meetings.*," +
                "COALESCE(stats.users_count, 0) AS users_count," +
                "COALESCE(stats.present_users_count, 0) AS present_users_count," +
                "(SELECT COUNT(id) FROM report_topics WHERE meeting_id=meetings.id) AS topics_count " +
            "FROM meetings " +
                "LEFT JOIN (" +
                    "SELECT " +
                        "meeting_id," +
                        "COUNT(id) AS users_count, " +
                        "SUM(" +
                            "CASE WHEN present THEN 1 END" +
                        ") AS present_users_count " +
                    "FROM users_meetings GROUP BY meeting_id " +
                ") AS stats ON stats.meeting_id=meetings.id " +
            "WHERE meetings.date BETWEEN :#{#dateFilter.getMin()} AND :#{#dateFilter.getMax()} AND EXISTS (" +
                "SELECT 1 FROM users WHERE id=:speakerId AND EXISTS (" +
                    "SELECT 1 FROM report_topics_speakers rts WHERE rts.speaker_id=:speakerId AND EXISTS (" +
                        "SELECT 1 FROM report_topics rt WHERE rt.meeting_id=meetings.id AND rt.id=rts.report_topic_id" +
                    ")" +
                ")" +
            ")" +
            "GROUP BY meetings.id, stats.users_count, stats.present_users_count",
        countQuery =
            "SELECT COUNT(id) FROM meetings " +
            "WHERE date BETWEEN :#{#dateFilter.getMin()} AND :#{#dateFilter.getMax()} AND EXISTS (" +
                "SELECT 1 FROM users WHERE id=:speakerId AND EXISTS (" +
                    "SELECT 1 FROM report_topics_speakers rts WHERE rts.speaker_id=:speakerId AND EXISTS (" +
                        "SELECT 1 FROM report_topics rt WHERE rt.meeting_id=meetings.id AND rt.id=rts.report_topic_id" +
                    ")" +
                ")" +
            ")"
    )
    Page<IMeetingWithStats> findAllWithFiltersBySpeaker(Pageable pageable, @Param("dateFilter") DateFilter dateFilter, @Param("speakerId") Integer speakerId);

    @Query(value =
        "SELECT " +
            "m " +
        "FROM Meeting m " +
            "LEFT JOIN FETCH m.reportTopics AS reportTopics " +
                "LEFT JOIN FETCH reportTopics.reportTopicSpeaker AS reportTopicSpeaker " +
                    "LEFT JOIN FETCH reportTopicSpeaker.speaker AS speaker WHERE m.id=:id"
    )
    Meeting findAllById(@Param("id") int id);

    @Query(value =
            "SELECT " +
                "m " +
            "FROM Meeting m " +
                "JOIN FETCH m.topicProposals AS topicProposals " +
                    "JOIN FETCH topicProposals.speaker"
    )
    Set<IMeetingTopicProposalsData> getMeetingsProposals();

    @Query(value =
            "SELECT " +
                "m " +
            "FROM Meeting m " +
                "JOIN FETCH m.reportTopics AS reportTopics " +
                    "LEFT JOIN FETCH reportTopics.reportTopicSpeaker AS reportTopicSpeaker " +
                    "JOIN FETCH reportTopics.speakerProposals AS speakerProposals " +
            "WHERE reportTopicSpeaker is null AND speakerProposals.speakerId=:speakerId AND m.date >= current_timestamp "
    )
    Set<ISpeakerProposalMeetingData> getSpeakerProposals(@Param("speakerId") int speakerId);

    @Query(value =
        "SELECT " +
            "m " +
        "FROM Meeting m " +
            "JOIN FETCH m.reportTopics AS reportTopics " +
                "LEFT JOIN FETCH reportTopics.reportTopicSpeaker AS reportTopicSpeaker " +
                "JOIN FETCH reportTopics.moderatorProposals AS moderatorProposals " +
        "WHERE reportTopicSpeaker is null AND moderatorProposals.speakerId=:speakerId AND m.date >= current_timestamp"
    )
    Set<IModeratorProposalMeetingData> getModeratorProposals(@Param("speakerId") int speakerId);

    @Query(nativeQuery = true, value = "UPDATE meetings SET address=:#{#meeting.getAddress()}, date=:#{#meeting.getDate()} WHERE id=:#{#meeting.getId()}")
    @Modifying
    void editMeeting(@Param("meeting") MeetingUpdatableData meeting);
}
