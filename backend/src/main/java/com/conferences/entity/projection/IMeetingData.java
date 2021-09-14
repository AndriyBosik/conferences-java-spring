package com.conferences.entity.projection;

import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

public interface IMeetingData {

    @Value("#{target.id}")
    Integer getId();

    @Value("#{target.title}")
    String getTitle();

    @Value("#{target.imagePath}")
    String getImagePath();

    @Value("#{target.date}")
    LocalDateTime getDate();
}
