package com.conferences.repository;

import com.conferences.entity.UserMeeting;
import com.conferences.entity.projection.IUserPresence;
import com.conferences.entity.projection.IUsersStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface IUserMeetingRepository extends JpaRepository<UserMeeting, Integer> {

    @Query(nativeQuery = true, value =
        "SELECT " +
            "COUNT (user_id) AS users_count," +
            "COALESCE(SUM (CASE WHEN present THEN 1 END), 0) AS present_users_count " +
        "FROM users_meetings WHERE meeting_id=:meetingId"
    )
    IUsersStats countAllByMeetingId(@Param("meetingId") int meetingId);

    UserMeeting findByUserIdAndMeetingId(int userId, int meetingId);

    @Query(value =
        "SELECT " +
            "um " +
        "FROM UserMeeting um " +
            "JOIN FETCH um.user " +
        "WHERE um.meetingId=:meetingId"
    )
    Set<IUserPresence> findAllByMeeting(@Param("meetingId") int meetingId);
}
