package com.conferences.service.implementation;

import com.conferences.entity.ModeratorProposal;
import com.conferences.entity.ReportTopicSpeaker;
import com.conferences.mapper.IMapper;
import com.conferences.repository.IModeratorProposalRepository;
import com.conferences.repository.IReportTopicSpeakerRepository;
import com.conferences.service.abstraction.IModeratorProposalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ModeratorProposalService implements IModeratorProposalService {

    private final IModeratorProposalRepository moderatorProposalRepository;
    private final IReportTopicSpeakerRepository reportTopicSpeakerRepository;
    private final IMapper<ModeratorProposal, ReportTopicSpeaker> mapper;

    @Autowired
    public ModeratorProposalService(IModeratorProposalRepository moderatorProposalRepository, IReportTopicSpeakerRepository reportTopicSpeakerRepository, IMapper<ModeratorProposal, ReportTopicSpeaker> mapper) {
        this.moderatorProposalRepository = moderatorProposalRepository;
        this.reportTopicSpeakerRepository = reportTopicSpeakerRepository;
        this.mapper = mapper;
    }

    @Override
    public void rejectProposal(int moderatorProposalId) {
        moderatorProposalRepository.deleteById(moderatorProposalId);
    }

    @Transactional
    @Override
    public boolean acceptProposal(ModeratorProposal moderatorProposal) {
        ReportTopicSpeaker reportTopicSpeaker = mapper.map(moderatorProposal);
        reportTopicSpeakerRepository.save(reportTopicSpeaker);
        moderatorProposalRepository.deleteById(moderatorProposal.getId());
        return true;
    }

    @Override
    public void create(ModeratorProposal moderatorProposal) {
        moderatorProposalRepository.save(moderatorProposal);
    }
}
