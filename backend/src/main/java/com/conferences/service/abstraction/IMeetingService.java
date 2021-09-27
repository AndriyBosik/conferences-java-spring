package com.conferences.service.abstraction;

import com.conferences.entity.Meeting;
import com.conferences.entity.ReportTopic;
import com.conferences.entity.UserMeeting;
import com.conferences.entity.projection.IMeetingWithStats;
import com.conferences.model.DateFilter;
import com.conferences.model.MeetingData;
import com.conferences.model.MeetingUpdatableData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * <p>
 *     Defines methods to work with meetings
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/27
 */
public interface IMeetingService {

    /**
     * <p>Returns page with meetings</p>
     * @param pageable contains information about page and sorting options
     * @param dateFilter contains information about filtering
     * @return list of {@link IMeetingWithStats} objects
     */
    Page<IMeetingWithStats> getMeetingsByPage(Pageable pageable, DateFilter dateFilter);

    /**
     * <p>Returns meetings for page for specified speaker with sorting and filtering options</p>
     * @param pageable contains information about page and sorting options
     * @param dateFilter contains information about filtering
     * @param speakerId id of speaker
     * @return list of {@link IMeetingWithStats} objects
     */
    Page<IMeetingWithStats> getMeetingsByPageAndSpeaker(Pageable pageable, DateFilter dateFilter, Integer speakerId);

    /**
     * <p>Returns meeting with its users stats</p>
     * @param meetingId id of meeting
     * @return an instance of {@link MeetingData}
     */
    MeetingData getMeeting(int meetingId);

    /**
     * <p>Returns all report topics will all of their data for meeting</p>
     * @param meetingId id of meeting
     * @return list of {@link ReportTopic}
     */
    List<ReportTopic> getMeetingTopics(int meetingId);

    /**
     * <p>Checks whatever user is joined to meeting</p>
     * @param userMeeting contains information about user and meeting
     * @return true if user is joined to meeting, false otherwise
     */
    boolean isUserJoined(UserMeeting userMeeting);

    /**
     * <p>Creates meeting</p>
     * @param meeting contains information about meeting
     * @return true if meeting was successfully created, false otherwise
     */
    boolean createMeeting(Meeting meeting);

    /**
     * <p>Edits meeting</p>
     * @param meeting contains information about meeting
     * @return true if meeting was successfully updated, false otherwise
     */
    boolean editMeeting(MeetingUpdatableData meeting);
}
