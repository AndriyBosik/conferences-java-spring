package com.conferences.repository;

import com.conferences.entity.ModeratorProposal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IModeratorProposalRepository extends JpaRepository<ModeratorProposal, Integer> {
}
