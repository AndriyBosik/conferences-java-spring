package com.conferences.service.implementation;

import com.conferences.entity.SpeakerProposal;
import com.conferences.entity.User;
import com.conferences.repository.ISpeakerProposalRepository;
import com.conferences.repository.IUserRepository;
import com.conferences.service.abstraction.ISecurityService;
import com.conferences.service.abstraction.ISpeakerProposalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpeakerProposalService implements ISpeakerProposalService {

    private final ISpeakerProposalRepository speakerProposalRepository;
    private final IUserRepository userRepository;
    private final ISecurityService securityService;

    @Autowired
    public SpeakerProposalService(ISpeakerProposalRepository speakerProposalRepository, IUserRepository userRepository, ISecurityService securityService) {
        this.speakerProposalRepository = speakerProposalRepository;
        this.userRepository = userRepository;
        this.securityService = securityService;
    }

    @Override
    public boolean proposeSpeaker(SpeakerProposal speakerProposal) {
        User user = userRepository.findByLogin(securityService.getUserLogin());
        speakerProposal.setSpeakerId(user.getId());
        speakerProposalRepository.save(speakerProposal);
        return true;
    }
}
