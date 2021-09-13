package com.conferences.entity.projection;

import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

public interface IMeeting {

    @Value("#{target.id}")
    Integer getId();

    @Value("#{target.title}")
    String getTitle();

    @Value("#{target.date}")
    LocalDateTime getDate();

    @Value("#{target.description}")
    String getDescription();

    @Value("#{target.image_path}")
    String getImagePath();

    @Value("#{target.address}")
    String getAddress();

    /*@Value("#{target.users_count}")
    Integer getUsersCount();

    @Value("#{target.present_users_count}")
    Integer getPresentUsersCount();*/

    @Value("#{target.topics_count}")
    Integer getTopicsCount();
}
