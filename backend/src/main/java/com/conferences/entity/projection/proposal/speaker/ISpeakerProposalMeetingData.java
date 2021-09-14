package com.conferences.entity.projection.proposal.speaker;

import com.conferences.entity.projection.IMeetingData;
import org.springframework.beans.factory.annotation.Value;

import java.util.Set;

public interface ISpeakerProposalMeetingData extends IMeetingData {

    @Value("#{target.reportTopics}")
    Set<ISpeakerReportTopic> getReportTopics();
}
