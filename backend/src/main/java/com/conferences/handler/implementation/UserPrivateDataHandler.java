package com.conferences.handler.implementation;

import com.conferences.entity.User;
import com.conferences.handler.abstraction.IPrivateDataHandler;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class UserPrivateDataHandler implements IPrivateDataHandler<User> {

    @Override
    public void clearPrivateData(User user) {
        log.info("Clearing user private data");
        user.setPassword("");
        user.setEmail("");
    }
}
