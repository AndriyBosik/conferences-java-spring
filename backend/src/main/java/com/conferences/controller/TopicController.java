package com.conferences.controller;

import com.conferences.entity.ReportTopic;
import com.conferences.entity.ReportTopicSpeaker;
import com.conferences.entity.TopicProposal;
import com.conferences.entity.User;
import com.conferences.handler.abstraction.IPrivateDataHandler;
import com.conferences.service.abstraction.ITopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 *     Controller which contains routes to handle topic requests
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/27
 */
@RestController
@RequestMapping("/api/topics")
public class TopicController {

    private final ITopicService topicService;

    @Autowired
    public TopicController(ITopicService topicService) {
        this.topicService = topicService;
    }

    /**
     * <p>Returns IDs of topics for which speaker proposed himself</p>
     * @param speakerId id of speaker
     * @param meetingId id of meeting to extract topic IDs from
     * @return list of IDs
     */
    @GetMapping("/get-speaker-proposed-topic-ids")
    public List<Integer> getSpeakerProposedTopicIds(@RequestParam("speakerId") Integer speakerId, @RequestParam("meetingId") Integer meetingId) {
        return topicService.getSpeakerProposedTopicIds(speakerId, meetingId);
    }

    /**
     * <p>Creates new topic from speaker proposed one</p>
     * @param topicProposal speaker proposed topic information
     * @return true if topic was successfully created, false otherwise
     */
    @PostMapping("/create-from-proposal")
    public boolean createFromProposal(@RequestBody TopicProposal topicProposal) {
        return topicService.createFromProposal(topicProposal.getId());
    }

    /**
     * <p>Creates new topic</p>
     * @param reportTopic information about new topic
     * @return true if topic was successfully created, false otherwise
     */
    @PostMapping("/create")
    public boolean createReportTopic(@Valid @RequestBody ReportTopic reportTopic) {
        return topicService.createReportTopic(reportTopic);
    }

    /**
     * <p>Edits topic</p>
     * @param reportTopic updated topic data
     * @return true if topic was successfully created, false otherwise
     */
    @PostMapping("/edit")
    public boolean editReportTopic(@Valid @RequestBody ReportTopic reportTopic) {
        return topicService.editReportTopic(reportTopic);
    }

    /**
     * <p>Returns list of report topics for meeting</p>
     * @param meetingId id of meeting
     * @return list of {@link ReportTopic} objects
     */
    @GetMapping("/get-by-meeting")
    public List<ReportTopic> getByMeeting(@RequestParam int meetingId) {
        return topicService.getByMeetingId(meetingId);
    }

    /**
     * <p>Assigns speaker for topic</p>
     * @param reportTopicSpeaker information about speaker for report topic
     * @return true if speaker was assigned successfully, false otherwise
     */
    @PostMapping("/set-speaker")
    public boolean setSpeaker(@RequestBody ReportTopicSpeaker reportTopicSpeaker) {
        topicService.setSpeaker(reportTopicSpeaker);
        return true;
    }
}
