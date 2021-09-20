package com.conferences.mapper;

import com.conferences.entity.ModeratorProposal;
import com.conferences.entity.ReportTopicSpeaker;
import org.springframework.stereotype.Component;

@Component
public class ModeratorProposalToReportTopicSpeakerMapper implements IMapper<ModeratorProposal, ReportTopicSpeaker> {

    @Override
    public ReportTopicSpeaker map(ModeratorProposal moderatorProposal) {
        ReportTopicSpeaker reportTopicSpeaker = new ReportTopicSpeaker();

        reportTopicSpeaker.setSpeakerId(moderatorProposal.getSpeakerId());
        reportTopicSpeaker.setReportTopicId(moderatorProposal.getReportTopicId());

        return reportTopicSpeaker;
    }
}
