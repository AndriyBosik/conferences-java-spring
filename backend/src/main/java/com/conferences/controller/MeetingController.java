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

    @GetMapping("/page/{page}/{items}")
    public Page<IMeetingWithStats> getMeetings(
        @PathVariable int page,
        @PathVariable int items,
        RequestSorter sorter
    ) {
        MeetingSorter meetingSorter = mapper.map(sorter);
        return meetingService.getMeetingsByPage(PageRequest.of(page - 1, items, meetingSorter.getSort()), meetingSorter.getDateFilter());
    }

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

    @GetMapping("/{meetingId}")
    public MeetingData getMeeting(@PathVariable int meetingId) {
        return meetingService.getMeeting(meetingId);
    }

    @GetMapping("/{meetingId}/topics")
    public List<ReportTopic> getMeetingTopics(@PathVariable int meetingId) {
        return meetingService.getMeetingTopics(meetingId);
    }

    @GetMapping("/check-user-joined")
    public boolean checkUserJoined(UserMeeting userMeeting) {
        return meetingService.isUserJoined(userMeeting);
    }

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

    @PostMapping("/edit")
    public boolean editMeeting(@Valid @RequestBody MeetingUpdatableData meeting) {
        meetingService.editMeeting(meeting);
        return true;
    }
}
