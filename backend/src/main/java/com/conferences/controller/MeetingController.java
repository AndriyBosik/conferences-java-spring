package com.conferences.controller;

import com.conferences.entity.ReportTopic;
import com.conferences.entity.projection.IMeetingWithStats;
import com.conferences.mapper.IMapper;
import com.conferences.model.MeetingData;
import com.conferences.model.MeetingSorter;
import com.conferences.model.RequestSorter;
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
    private final IMapper<RequestSorter, MeetingSorter> mapper;

    @Autowired
    public MeetingController(IMeetingService meetingService, IMapper<RequestSorter, MeetingSorter> mapper) {
        this.meetingService = meetingService;
        this.mapper = mapper;
    }

    @GetMapping("/page/{page}/{items}")
    public Page<IMeetingWithStats> getMeetings(
        @PathVariable int page,
        @PathVariable int items,
        RequestSorter sorter
    ) {
        MeetingSorter meetingSorter = mapper.map(sorter);

        Page<IMeetingWithStats> meetings = meetingService.getMeetingsByPage(PageRequest.of(page - 1, items, meetingSorter.getSort()), meetingSorter.getDateFilter());
        for (IMeetingWithStats meeting: meetings.getContent()) {
            System.out.println(meeting.getDate() + " " + meeting.getTitle());
        }
        return meetings;
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
