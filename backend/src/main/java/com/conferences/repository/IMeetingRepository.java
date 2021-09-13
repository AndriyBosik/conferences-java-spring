package com.conferences.repository;

import com.conferences.entity.Meeting;
import com.conferences.entity.projection.IMeeting;
import com.conferences.model.DateFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface IMeetingRepository extends Repository<Meeting, Integer>, JpaSpecificationExecutor<Meeting> {

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
    Page<IMeeting> findAllWithSpecification(Pageable pageable, @Param("dateFilter") DateFilter dateFilter);
}
