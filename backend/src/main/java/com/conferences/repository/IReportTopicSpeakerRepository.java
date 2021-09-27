package com.conferences.repository;

import com.conferences.entity.ReportTopicSpeaker;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>
 *     Defines methods to work with report_topic_speakers table in database
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/27
 */
public interface IReportTopicSpeakerRepository extends JpaRepository<ReportTopicSpeaker, Integer> {

    /**
     * <p>Deletes entity from database by id of report topic</p>
     * @param reportTopicId id of report topic
     */
    void deleteByReportTopicId(int reportTopicId);
}
