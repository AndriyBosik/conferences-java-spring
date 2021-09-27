package com.conferences.repository;

import com.conferences.entity.TopicProposal;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>
 *     Defines methods to work with topic proposals in database
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/27
 */
public interface ITopicProposalRepository extends JpaRepository<TopicProposal, Integer> {
}
