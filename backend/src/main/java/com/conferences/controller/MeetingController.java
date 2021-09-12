package com.conferences.controller;

import com.conferences.entity.Meeting;
import com.conferences.entity.ReportTopic;
import com.conferences.entity.projection.IMeeting;
import com.conferences.repository.IMeetingRepository;
import com.conferences.service.abstraction.IMeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/meetings")
public class MeetingController {

    private final IMeetingService meetingService;
    private final IMeetingRepository repository;

    @Autowired
    public MeetingController(IMeetingService meetingService, IMeetingRepository repository) {
        this.meetingService = meetingService;
        this.repository = repository;
    }

    @GetMapping("/page/{pageNumber}/{items}")
    public Page<IMeeting> getMeetings(@PathVariable int pageNumber, @PathVariable int items) {
        return meetingService.getMeetingsByPage(PageRequest.of(pageNumber - 1, items));
    }

    @GetMapping("/{meetingId}/topics")
    public List<ReportTopic> getMeetingTopics(@PathVariable int meetingId) {
        return meetingService.getMeetingTopics(meetingId);
    }
}
