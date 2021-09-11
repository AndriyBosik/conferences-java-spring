package com.conferences.controller;

import com.conferences.entity.Meeting;
import com.conferences.entity.ReportTopic;
import com.conferences.service.abstraction.IMeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/meetings")
public class MeetingController {

    private final IMeetingService meetingService;

    @Autowired
    public MeetingController(IMeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @GetMapping("/page/{pageNumber}/{items}")
    public Page<Meeting> getMeeting(@PathVariable int pageNumber, @PathVariable int items) {
        return meetingService.getMeetingsByPage(PageRequest.of(pageNumber - 1, items));
    }

    @GetMapping("/{meetingId}/topics")
    public List<ReportTopic> getMeetingTopics(@PathVariable int meetingId) {
        return meetingService.getMeetingTopics(meetingId);
    }
}
