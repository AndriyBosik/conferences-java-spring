package com.conferences.entity.projection.proposal.topic;

import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.List;

public interface IMeetingData {

    @Value("#{target.id}")
    Integer getId();

    @Value("#{target.title}")
    String getTitle();

    @Value("#{target.imagePath}")
    String getImagePath();

    @Value("#{target.date}")
    LocalDateTime getDate();

    @Value("#{target.topicProposals}")
    List<ITopicProposal> getTopicProposals();
}
