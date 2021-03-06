package com.conferences.entity.projection.proposal.topic;

import com.conferences.entity.projection.IUserPublicData;
import org.springframework.beans.factory.annotation.Value;

public interface ITopicProposal {

    @Value("#{target.id}")
    Integer getId();

    @Value("#{target.topicTitle}")
    String getTopicTitle();

    @Value("#{target.speaker}")
    IUserPublicData getSpeaker();
}