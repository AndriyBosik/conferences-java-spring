package com.conferences.entity.projection.proposal;

import org.springframework.beans.factory.annotation.Value;

public interface IProposal {

    @Value("#{target.id}")
    Integer getId();

    @Value("#{target.speakerId}")
    Integer getSpeakerId();
}
