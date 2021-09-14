package com.conferences.repository;

import com.conferences.entity.Meeting;
import com.conferences.entity.projection.IMeetingWithStats;
import com.conferences.entity.projection.proposal.moderator.IModeratorProposalMeetingData;
import com.conferences.entity.projection.proposal.speaker.ISpeakerProposalMeetingData;
import com.conferences.entity.projection.proposal.topic.IMeetingTopicProposalsData;
import com.conferences.model.DateFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    Page<IMeetingWithStats> findAllWithSpecification(Pageable pageable, @Param("dateFilter") DateFilter dateFilter);

    @Query(value =
        "SELECT " +
            "m " +
        "FROM Meeting m " +
            "JOIN FETCH m.reportTopics AS reportTopics " +
                "JOIN FETCH reportTopics.reportTopicSpeaker AS reportTopicSpeaker " +
                    "JOIN FETCH reportTopicSpeaker.speaker AS speaker WHERE m.id=:id"
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
}
