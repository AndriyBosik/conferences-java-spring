package com.conferences.config;

import com.conferences.handler.abstraction.IJwtHandler;
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

/**
 * <p>
 *     Handles user authorization
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/27
 */
@Log4j2
@Component
public class JwtFilter extends GenericFilterBean {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String HEADER_TOKEN_PREFIX = "Bearer ";

    private final IJwtHandler jwtHandler;

    @Autowired
    public JwtFilter(IJwtHandler jwtHandler) {
        this.jwtHandler = jwtHandler;
    }

    /**
     * <p>
     *     Validates token. And authenticate user if success
     * </p>
     * @param servletRequest an instance of {@link ServletRequest} class
     * @param servletResponse an instance of {@link ServletResponse} class
     * @param filterChain an instance of {@link FilterChain} class
     * @throws IOException may be thrown during filter executing
     * @throws ServletException may be thrown during filter executing
     */
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

    /**
     * <p>
     *     Parses Authorization header to extract token value
     * </p>
     * @param request an instance of {@link HttpServletRequest} to extract header value from
     * @return extracted token
     */
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
