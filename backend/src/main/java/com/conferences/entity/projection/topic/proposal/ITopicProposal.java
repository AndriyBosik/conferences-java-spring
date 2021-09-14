package com.conferences.entity.projection.topic.proposal;

import com.conferences.entity.User;
import org.springframework.beans.factory.annotation.Value;

public interface ITopicProposal {

    @Value("#{target.id}")
    Integer getId();

    @Value("#{target.topicTitle}")
    String getTopicTitle();

    @Value("#{target.speaker}")
    ISpeaker getSpeaker();
}