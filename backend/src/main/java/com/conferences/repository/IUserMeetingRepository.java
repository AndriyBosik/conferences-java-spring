package com.conferences.repository;

import com.conferences.entity.UserMeeting;
import com.conferences.entity.projection.IUsersPresence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IUserMeetingRepository extends JpaRepository<UserMeeting, Integer> {

    @Query(nativeQuery = true, value =
        "SELECT " +
            "COUNT (user_id) AS users_count," +
            "COALESCE(SUM (CASE WHEN present THEN 1 END), 0) AS present_users_count " +
        "FROM users_meetings WHERE meeting_id=1;"
    )
    IUsersPresence countAllByMeetingId(int meetingId);

    UserMeeting findByUserIdAndMeetingId(int userId, int meetingId);
}
