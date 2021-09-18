package com.conferences.entity.projection;

import org.springframework.beans.factory.annotation.Value;

public interface IUserPresence {

    @Value("#{target.id}")
    Integer getId();

    @Value("#{target.meetingId}")
    Integer getMeetingId();

    @Value("#{target.present}")
    Boolean isPresent();

    @Value("#{target.user}")
    IUserPublicData getUser();
}
