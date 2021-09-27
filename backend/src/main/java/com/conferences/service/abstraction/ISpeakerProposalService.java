package com.conferences.service.abstraction;

import com.conferences.entity.SpeakerProposal;

/**
 * <p>
 *     Defines methods to work with speaker proposals
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/27
 */
public interface ISpeakerProposalService {

    /**
     * <p>Creates speaker proposal</p>
     * @param speakerProposal contains information about speaker proposal
     * @return true if proposal was successfully created, false otherwise
     */
    boolean proposeSpeaker(SpeakerProposal speakerProposal);
}
