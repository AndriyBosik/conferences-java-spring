package com.conferences.entity.projection;

import org.springframework.beans.factory.annotation.Value;

public interface IUsersPresence {

    @Value("#{target.users_count}")
    int getUsersCount();

    @Value("#{target.present_users_count}")
    int getPresentUsersCount();
}
