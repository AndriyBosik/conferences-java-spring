package com.conferences.service.implementation;

import com.conferences.entity.User;
import com.conferences.handler.abstraction.IJwtHandler;
import com.conferences.model.Account;
import com.conferences.service.abstraction.ISecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

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
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getUsername();
        }
        return null;
    }

    @Override
    public String reAuthenticateUser(User user) {
        UserDetails userDetails = new Account(user);
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        return jwtHandler.generateToken(user);
    }
}
