package com.conferences.mapper;

import com.conferences.entity.ModeratorProposal;
import com.conferences.entity.ReportTopicSpeaker;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class ModeratorProposalToReportTopicSpeakerMapper implements IMapper<ModeratorProposal, ReportTopicSpeaker> {

    @Override
    public ReportTopicSpeaker map(ModeratorProposal moderatorProposal) {
        log.info("Mapping {} to {}", ModeratorProposal.class.getTypeName(), ReportTopicSpeaker.class.getTypeName());
        ReportTopicSpeaker reportTopicSpeaker = new ReportTopicSpeaker();

        reportTopicSpeaker.setSpeakerId(moderatorProposal.getSpeakerId());
        reportTopicSpeaker.setReportTopicId(moderatorProposal.getReportTopicId());

        return reportTopicSpeaker;
    }
}
