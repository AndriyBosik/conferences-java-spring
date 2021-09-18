package com.conferences.repository;

import com.conferences.entity.TopicProposal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITopicProposalRepository extends JpaRepository<TopicProposal, Integer> {
}
