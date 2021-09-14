package com.conferences.entity.projection.proposal.moderator;

import com.conferences.entity.projection.IMeetingData;
import org.springframework.beans.factory.annotation.Value;

import java.util.Set;

public interface IModeratorProposalMeetingData extends IMeetingData {

    @Value("#{target.reportTopics}")
    Set<IModeratorReportTopic> getReportTopics();
}
