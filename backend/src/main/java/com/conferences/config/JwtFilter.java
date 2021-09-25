package com.conferences.config;

import com.conferences.handler.implementation.JwtHandler;
import com.conferences.model.Account;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Log4j2
@Component
public class JwtFilter extends GenericFilterBean {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String HEADER_TOKEN_PREFIX = "Bearer ";

    private final JwtHandler jwtHandler;

    @Autowired
    public JwtFilter(JwtHandler jwtHandler) {
        this.jwtHandler = jwtHandler;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = getTokenFromRequest((HttpServletRequest) servletRequest);
        if (token != null && jwtHandler.validateToken(token)) {
            UserDetails userDetails = new Account(jwtHandler.getUserFromToken(token));
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            log.info("User is successfully authenticated");
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        log.info("Getting token from request");
        String bearer = request.getHeader(AUTHORIZATION_HEADER);
        if (bearer == null) {
            log.info("Token does not exist");
            return null;
        }
        if (bearer.startsWith(HEADER_TOKEN_PREFIX)) {
            return bearer.substring(HEADER_TOKEN_PREFIX.length());
        }
        return null;
    }
}
