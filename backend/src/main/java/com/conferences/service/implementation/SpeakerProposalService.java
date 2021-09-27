package com.conferences.service.implementation;

import com.conferences.entity.SpeakerProposal;
import com.conferences.entity.User;
import com.conferences.repository.ISpeakerProposalRepository;
import com.conferences.repository.IUserRepository;
import com.conferences.service.abstraction.ISecurityService;
import com.conferences.service.abstraction.ISpeakerProposalService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * {@inheritDoc}
 */
@Log4j2
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

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean proposeSpeaker(SpeakerProposal speakerProposal) {
        User user = userRepository.findByLogin(securityService.getUserLogin());
        speakerProposal.setSpeakerId(user.getId());
        log.info("Saving speaker proposal");
        speakerProposalRepository.save(speakerProposal);
        return true;
    }
}
