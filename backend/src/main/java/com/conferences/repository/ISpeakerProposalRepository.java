package com.conferences.repository;

import com.conferences.entity.SpeakerProposal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISpeakerProposalRepository extends JpaRepository<SpeakerProposal, Integer> {
}
