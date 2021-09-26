package com.conferences.mapper;

import com.conferences.entity.ModeratorProposal;
import com.conferences.entity.ReportTopicSpeaker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ModeratorProposalToReportTopicSpeakerMapperTest {

    private static ModeratorProposalToReportTopicSpeakerMapper mapper;

    @BeforeAll
    private static void beforeAll() {
        mapper = new ModeratorProposalToReportTopicSpeakerMapper();
    }

    @Test
    public void shouldMapModeratorProposalToReportTopicSpeaker() {
        ModeratorProposal moderatorProposal = new ModeratorProposal(0, 5, 8);
        ReportTopicSpeaker reportTopicSpeaker = mapper.map(moderatorProposal);

        assertEquals(5, reportTopicSpeaker.getSpeakerId());
        assertEquals(8, reportTopicSpeaker.getReportTopicId());
    }
}
