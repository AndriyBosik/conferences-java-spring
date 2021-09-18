package com.conferences.entity.projection;

import org.springframework.beans.factory.annotation.Value;

public interface IUserPublicData {

    @Value("#{target.id}")
    Integer getId();

    @Value("#{target.name}")
    String getName();

    @Value("#{target.surname}")
    String getSurname();

    @Value("#{target.imagePath}")
    String getImagePath();
}
