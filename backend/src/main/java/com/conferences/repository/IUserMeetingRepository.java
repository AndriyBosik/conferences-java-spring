package com.conferences.repository;

import com.conferences.entity.UserMeeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserMeetingRepository extends JpaRepository<UserMeeting, Integer> {

    int countAllByMeetingId(int meetingId);
}
