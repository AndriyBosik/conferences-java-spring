package com.conferences.entity.projection.proposal;

import org.springframework.beans.factory.annotation.Value;

import java.util.Set;

public interface IModeratorReportTopic {

    @Value("#{target.id}")
    Integer getId();

    @Value("#{target.title}")
    String getTitle();

    @Value("#{target.moderatorProposals}")
    Set<IProposal> getModeratorProposals();
}
