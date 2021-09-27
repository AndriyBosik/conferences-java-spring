package com.conferences.repository;

import com.conferences.entity.UserMeeting;
import com.conferences.entity.projection.IUserPresence;
import com.conferences.entity.projection.IUsersStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

/**
 * <p>
 *     Defines methods to work with users_meetings table in database
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/27
 */
public interface IUserMeetingRepository extends JpaRepository<UserMeeting, Integer> {

    /**
     * <p>Extracts users presence information for specified meeting</p>
     * @param meetingId id of meeting
     * @return an instance of {@link IUsersStats} class
     */
    @Query(nativeQuery = true, value =
        "SELECT " +
            "COUNT (user_id) AS users_count," +
            "COALESCE(SUM (CASE WHEN present THEN 1 END), 0) AS present_users_count " +
        "FROM users_meetings WHERE meeting_id=:meetingId"
    )
    IUsersStats countAllByMeetingId(@Param("meetingId") int meetingId);

    /**
     * <p>Extracts {@link UserMeeting} object by user and meeting</p>
     * @param userId id of user
     * @param meetingId id of meeting
     * @return an instance of {@link UserMeeting} class
     */
    UserMeeting findByUserIdAndMeetingId(int userId, int meetingId);

    /**
     * <p>Extracts user presence information for meeting</p>
     * @param meetingId id of meeting
     * @return set of {@link IUserPresence} objects
     */
    @Query(value =
        "SELECT " +
            "um " +
        "FROM UserMeeting um " +
            "JOIN FETCH um.user " +
        "WHERE um.meetingId=:meetingId"
    )
    Set<IUserPresence> findAllByMeeting(@Param("meetingId") int meetingId);
}
