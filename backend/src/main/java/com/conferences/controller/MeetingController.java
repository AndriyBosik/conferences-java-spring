package com.conferences.controller;

import com.conferences.entity.Meeting;
import com.conferences.entity.ReportTopic;
import com.conferences.entity.UserMeeting;
import com.conferences.entity.projection.IMeetingWithStats;
import com.conferences.handler.abstraction.IFileHandler;
import com.conferences.mapper.IMapper;
import com.conferences.model.MeetingData;
import com.conferences.model.MeetingSorter;
import com.conferences.model.MeetingUpdatableData;
import com.conferences.model.RequestSorter;
import com.conferences.service.abstraction.IMeetingService;
import com.conferences.service.abstraction.IStorageService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 *     Controller which contains routes to handle meeting requests
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/27
 */
@Log4j2
@RestController
@RequestMapping("/api/meetings")
public class MeetingController {

    private final IMeetingService meetingService;
    private final IMapper<RequestSorter, MeetingSorter> mapper;
    private final IStorageService storageService;
    private final IFileHandler fileHandler;

    @Autowired
    public MeetingController(IMeetingService meetingService, IMapper<RequestSorter, MeetingSorter> mapper, IStorageService storageService, IFileHandler fileHandler) {
        this.meetingService = meetingService;
        this.mapper = mapper;
        this.storageService = storageService;
        this.fileHandler = fileHandler;
    }

    /**
     * <p>Returns page with meetings</p>
     * @param page page number
     * @param items count of needed items
     * @param sorter contains information about sorting and filtering
     * @return page with meetings
     */
    @GetMapping("/page/{page}/{items}")
    public Page<IMeetingWithStats> getMeetings(
        @PathVariable int page,
        @PathVariable int items,
        RequestSorter sorter
    ) {
        MeetingSorter meetingSorter = mapper.map(sorter);
        return meetingService.getMeetingsByPage(PageRequest.of(page - 1, items, meetingSorter.getSort()), meetingSorter.getDateFilter());
    }

    /**
     * <p>Returns meetings which speaker take part in</p>
     * @param page page number
     * @param items count of needed items
     * @param sorter contains information about sorting and filtering
     * @param speakerId id of speaker
     * @return page with meetings
     */
    @GetMapping("/speaker/{page}/{items}")
    public Page<IMeetingWithStats> getMeetingsByPageAndSpeaker(
        @PathVariable int page,
        @PathVariable int items,
        RequestSorter sorter,
        Integer speakerId
    ) {
        MeetingSorter meetingSorter = mapper.map(sorter);
        return meetingService.getMeetingsByPageAndSpeaker(PageRequest.of(page - 1, items, meetingSorter.getSort()), meetingSorter.getDateFilter(), speakerId);
    }

    /**
     * <p>Returns meeting data</p>
     * @param meetingId id of meeting
     * @return instance of {@link MeetingData}
     */
    @GetMapping("/{meetingId}")
    public MeetingData getMeeting(@PathVariable int meetingId) {
        return meetingService.getMeeting(meetingId);
    }

    /**
     * <p>Returns report topics for requested meeting</p>
     * @param meetingId id of meeting
     * @return list of report topics
     */
    @GetMapping("/{meetingId}/topics")
    public List<ReportTopic> getMeetingTopics(@PathVariable int meetingId) {
        return meetingService.getMeetingTopics(meetingId);
    }

    /**
     * <p>Check whatever user was joined to specified meeting</p>
     * @param userMeeting contains information about user and meeting
     * @return true if user was joined, false otherwise
     */
    @GetMapping("/check-user-joined")
    public boolean checkUserJoined(UserMeeting userMeeting) {
        return meetingService.isUserJoined(userMeeting);
    }

    /**
     * <p>Creates meeting</p>
     * @param file meeting image file
     * @param meeting contains meeting information
     * @return true if meeting was successfully created, false otherwise
     */
    @PostMapping(value = "/create", consumes = {"multipart/form-data"})
    public boolean createMeeting(@RequestPart("file") MultipartFile file, @Valid @RequestPart("meeting") Meeting meeting) {
        log.info("Creating meeting");
        String imagePath = fileHandler.addTimestampToFilename("meeting", file.getOriginalFilename());
        if (storageService.store(file, imagePath, "/meetings")) {
            log.info("Meeting's image is saved");
            meeting.setImagePath(imagePath);
            meetingService.createMeeting(meeting);
            return true;
        }
        log.warn("Meeting's image is not saved. Meeting not saved");
        return false;
    }

    /**
     * <p>Edits meeting</p>
     * @param meeting contains meeting information which has to be updated
     * @return true if meeting was successfully edited, false otherwise
     */
    @PostMapping("/edit")
    public boolean editMeeting(@Valid @RequestBody MeetingUpdatableData meeting) {
        meetingService.editMeeting(meeting);
        return true;
    }
}
