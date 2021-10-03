package com.conferences.service;

import com.conferences.entity.ModeratorProposal;
import com.conferences.entity.ReportTopicSpeaker;
import com.conferences.mapper.IMapper;
import com.conferences.repository.IModeratorProposalRepository;
import com.conferences.repository.IReportTopicRepository;
import com.conferences.repository.IReportTopicSpeakerRepository;
import com.conferences.service.implementation.ModeratorProposalService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

public class ModeratorProposalServiceTest {

    private static ModeratorProposalService moderatorProposalService;
    private static IModeratorProposalRepository moderatorProposalRepository;
    private static IReportTopicSpeakerRepository reportTopicSpeakerRepository;
    private static IMapper<ModeratorProposal, ReportTopicSpeaker> mapper;

    @Test
    public void shouldSaveModeratorProposal() {
        List<ModeratorProposal> proposals = new ArrayList<>();

        moderatorProposalRepository = Mockito.mock(IModeratorProposalRepository.class);
        Mockito.doAnswer(invocationOnMock -> {
            proposals.add(invocationOnMock.getArgument(0));
            return null;
        }).when(moderatorProposalRepository).save(any(ModeratorProposal.class));

        moderatorProposalService = new ModeratorProposalService(moderatorProposalRepository, reportTopicSpeakerRepository, mapper);

        moderatorProposalService.create(new ModeratorProposal(10, 5, 7));

        assertEquals(1, proposals.size());
        assertEquals(10, proposals.get(0).getId());
        assertEquals(5, proposals.get(0).getSpeakerId());
        assertEquals(7, proposals.get(0).getReportTopicId());
    }

    @Test
    public void shouldRejectProposal() {
        List<ModeratorProposal> proposals = new ArrayList<>();
        proposals.add(new ModeratorProposal(1, 2, 3));
        proposals.add(new ModeratorProposal(2, 2, 4));
        proposals.add(new ModeratorProposal(3, 5, 9));
        proposals.add(new ModeratorProposal(4, 3, 1));
        proposals.add(new ModeratorProposal(5, 8, 9));
        final int proposalsCount = proposals.size();

        moderatorProposalRepository = Mockito.mock(IModeratorProposalRepository.class);
        Mockito.doAnswer(invocationOnMock -> {
            proposals.removeIf(proposal -> proposal.getId() == invocationOnMock.getArgument(0));
            return null;
        }).when(moderatorProposalRepository).deleteById(any(Integer.class));

        moderatorProposalService = new ModeratorProposalService(moderatorProposalRepository, reportTopicSpeakerRepository, mapper);
        moderatorProposalService.rejectProposal(5);

        assertEquals(proposalsCount - 1, proposals.size());
        assertFalse(proposals.stream().anyMatch(proposal -> proposal.getId() == 5));
    }

    @Test
    public void shouldAcceptModeratorProposal() {
        List<ModeratorProposal> moderatorProposals = new ArrayList<>();
        moderatorProposals.add(new ModeratorProposal(1, 2, 3));
        moderatorProposals.add(new ModeratorProposal(2, 4, 6));
        final int moderatorProposalsCount = moderatorProposals.size();
        final ModeratorProposal toTest = moderatorProposals.get(1);

        List<ReportTopicSpeaker> reportTopicSpeakers = new ArrayList<>();

        mapper = (IMapper<ModeratorProposal, ReportTopicSpeaker>) Mockito.mock(IMapper.class);
        Mockito.doAnswer(invocationOnMock -> new ReportTopicSpeaker(
            10,
            ((ModeratorProposal) invocationOnMock.getArgument(0)).getReportTopicId(),
            ((ModeratorProposal) invocationOnMock.getArgument(0)).getSpeakerId()
        )).when(mapper).map(any(ModeratorProposal.class));

        reportTopicSpeakerRepository = Mockito.mock(IReportTopicSpeakerRepository.class);
        Mockito.doAnswer(invocationOnMock -> {
            reportTopicSpeakers.add(invocationOnMock.getArgument(0));
            return null;
        }).when(reportTopicSpeakerRepository).save(any(ReportTopicSpeaker.class));

        moderatorProposalRepository = Mockito.mock(IModeratorProposalRepository.class);
        Mockito.doAnswer(invocationOnMock -> {
            moderatorProposals.removeIf(proposal -> proposal.getId() == invocationOnMock.getArgument(0));
            return null;
        }).when(moderatorProposalRepository).deleteById(any(Integer.class));

        moderatorProposalService = new ModeratorProposalService(moderatorProposalRepository, reportTopicSpeakerRepository, mapper);
        moderatorProposalService.acceptProposal(toTest);

        assertEquals(moderatorProposalsCount - 1, moderatorProposals.size());
        assertEquals(1, reportTopicSpeakers.size());
        assertEquals(toTest.getSpeakerId(), reportTopicSpeakers.get(0).getSpeakerId());
        assertEquals(toTest.getReportTopicId(), reportTopicSpeakers.get(0).getReportTopicId());
    }

}
