package com.conferences.service.abstraction;

import com.conferences.entity.ReportTopic;
import com.conferences.entity.ReportTopicSpeaker;
import com.conferences.entity.TopicProposal;

import java.util.List;

/**
 * <p>
 *     Defines methods to work with topics
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/27
 */
public interface ITopicService {

    /**
     * <p>Returns IDs of topics for which speaker proposed himself</p>
     * @param speakerId id of speaker
     * @param meetingId id of meeting
     * @return list of IDs
     */
    List<Integer> getSpeakerProposedTopicIds(int speakerId, int meetingId);

    /**
     * <p>Creates new report topic from topic proposal</p>
     * @param topicProposalId id of topic proposal to create report topic from
     * @return true if topic proposal was successfully created, false otherwise
     */
    boolean createFromProposal(int topicProposalId);

    /**
     * <p>Creates new report topic</p>
     * @param reportTopic contains information about report topic
     * @return true if report topic was successfully created, false otherwise
     */
    boolean createReportTopic(ReportTopic reportTopic);

    /**
     * <p>Edits report topic</p>
     * @param reportTopic contains report topic's updated information
     * @return true if report topic was successfully updated, false otherwise
     */
    boolean editReportTopic(ReportTopic reportTopic);

    /**
     * <p>Returns report topics for meeting with speakers</p>
     * @param meetingId id of meeting
     * @return list of {@link ReportTopic}
     */
    List<ReportTopic> getByMeetingId(int meetingId);

    /**
     * <p>Assigns speaker for report topic</p>
     * @param reportTopicSpeaker contains information about speaker and report topic
     */
    void setSpeaker(ReportTopicSpeaker reportTopicSpeaker);
}
