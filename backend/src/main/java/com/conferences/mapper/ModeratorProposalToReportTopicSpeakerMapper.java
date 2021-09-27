package com.conferences.mapper;

import com.conferences.entity.ModeratorProposal;
import com.conferences.entity.ReportTopicSpeaker;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

/**
 * {@inheritDoc}
 */
@Log4j2
@Component
public class ModeratorProposalToReportTopicSpeakerMapper implements IMapper<ModeratorProposal, ReportTopicSpeaker> {

    /**
     * {@inheritDoc}
     * <p>Maps an instance of {@link ModeratorProposal} class to an instance of {@link ReportTopicSpeaker} class</p>
     */
    @Override
    public ReportTopicSpeaker map(ModeratorProposal moderatorProposal) {
        log.info("Mapping {} to {}", ModeratorProposal.class.getTypeName(), ReportTopicSpeaker.class.getTypeName());
        ReportTopicSpeaker reportTopicSpeaker = new ReportTopicSpeaker();

        reportTopicSpeaker.setSpeakerId(moderatorProposal.getSpeakerId());
        reportTopicSpeaker.setReportTopicId(moderatorProposal.getReportTopicId());

        return reportTopicSpeaker;
    }
}
