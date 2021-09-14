package com.conferences.entity.projection;

import org.springframework.beans.factory.annotation.Value;

public interface IReportTopicData {

    @Value("#{target.id}")
    Integer getId();

    @Value("#{target.title}")
    String getTitle();
}
