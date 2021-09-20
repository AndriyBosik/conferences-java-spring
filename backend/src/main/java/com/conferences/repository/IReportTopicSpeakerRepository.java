package com.conferences.repository;

import com.conferences.entity.ReportTopicSpeaker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IReportTopicSpeakerRepository extends JpaRepository<ReportTopicSpeaker, Integer> {
}
