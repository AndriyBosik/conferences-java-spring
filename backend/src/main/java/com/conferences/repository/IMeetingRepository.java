package com.conferences.repository;

import com.conferences.entity.Meeting;
import com.conferences.entity.projection.IMeeting;
import com.conferences.model.Filter;
import com.conferences.model.Sorter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IMeetingRepository extends PagingAndSortingRepository<Meeting, Integer> {

    @Query(value =
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
                    "FROM users_meetings GROUP BY meeting_id" +
        ") AS stats ON stats.meeting_id=meetings.id " +
        //"WHERE :#{#filter.getCondition()} " +
        "GROUP BY meetings.id, stats.users_count, stats.present_users_count " +
        "ORDER BY :#{#sorter.getColumn()}",
        countQuery = "SELECT COUNT(id) FROM meetings",
        nativeQuery = true
    )
    Page<IMeeting> getWithUsersCountAndTopicsCount(Pageable pageable, @Param("sorter") Sorter sorter);
}
