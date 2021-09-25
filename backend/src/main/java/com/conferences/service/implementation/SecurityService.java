package com.conferences.service.implementation;

import com.conferences.entity.User;
import com.conferences.handler.abstraction.IJwtHandler;
import com.conferences.model.Account;
import com.conferences.service.abstraction.ISecurityService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class SecurityService implements ISecurityService {

    private final IJwtHandler jwtHandler;

    @Autowired
    public SecurityService(IJwtHandler jwtHandler) {
        this.jwtHandler = jwtHandler;
    }

    @Override
    public String getUserLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Extracting user login");
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getUsername();
        }
        log.warn("Unable to extract");
        return null;
    }

    @Override
    public String reAuthenticateUser(User user) {
        log.info("Re-authenticating user");
        UserDetails userDetails = new Account(user);
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        return jwtHandler.generateToken(user);
    }
}
