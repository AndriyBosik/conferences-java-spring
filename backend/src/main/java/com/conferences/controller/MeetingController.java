package com.conferences.controller;

import com.conferences.entity.Meeting;
import com.conferences.entity.ReportTopic;
import com.conferences.entity.projection.IMeetingWithStats;
import com.conferences.model.MeetingData;
import com.conferences.model.MeetingSorter;
import com.conferences.repository.IMeetingRepository;
import com.conferences.service.abstraction.IMeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meetings")
public class MeetingController {

    private final IMeetingService meetingService;

    @Autowired
    public MeetingController(IMeetingService meetingService, IMeetingRepository repository) {
        this.meetingService = meetingService;
    }

    @GetMapping("/page/{page}/{items}")
    public Page<IMeetingWithStats> getMeetings(
        @PathVariable int page,
        @PathVariable int items,
        @RequestBody MeetingSorter sorter
    ) {
        return meetingService.getMeetingsByPage(PageRequest.of(page - 1, items, sorter.getSort()), sorter.getDateFilter());
    }

    @GetMapping("/{meetingId}")
    public MeetingData getMeeting(@PathVariable int meetingId) {
        return meetingService.getMeeting(meetingId);
    }

    @GetMapping("/{meetingId}/topics")
    public List<ReportTopic> getMeetingTopics(@PathVariable int meetingId) {
        return meetingService.getMeetingTopics(meetingId);
    }
}
