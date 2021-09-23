package com.conferences.handler.implementation;

import com.conferences.entity.User;
import com.conferences.handler.abstraction.IPrivateDataHandler;
import org.springframework.stereotype.Component;

@Component
public class UserPrivateDataHandler implements IPrivateDataHandler<User> {

    @Override
    public void clearPrivateData(User user) {
        user.setPassword("");
        user.setEmail("");
    }
}
