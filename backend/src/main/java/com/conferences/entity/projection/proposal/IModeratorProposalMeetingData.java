package com.conferences.entity.projection.proposal;

import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.Set;

public interface IModeratorProposalMeetingData {

    @Value("#{target.id}")
    Integer getId();

    @Value("#{target.title}")
    String getTitle();

    @Value("#{target.imagePath}")
    String getImagePath();

    @Value("#{target.date}")
    LocalDateTime getDate();

    @Value("#{target.reportTopics}")
    Set<IModeratorReportTopic> getReportTopics();
}
