package com.conferences.entity.projection.proposal.topic;

import org.springframework.beans.factory.annotation.Value;

public interface ISpeaker {

    @Value("#{target.name}")
    String getName();

    @Value("#{target.surname}")
    String getSurname();

    @Value("#{target.imagePath}")
    String getImagePath();
}
