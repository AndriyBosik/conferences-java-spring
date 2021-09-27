package com.conferences.repository;

import com.conferences.entity.SpeakerProposal;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>
 *     Defines methods to work with speaker_proposals table in database
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/27
 */
public interface ISpeakerProposalRepository extends JpaRepository<SpeakerProposal, Integer> {
}
