package com.conferences.config;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 *     Request filter
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/27
 */
@Component
public class CorsFilter extends OncePerRequestFilter {

    /**
     * <p>Sets Access-Control-Allow-Credentials header value to true</p>
     *
     * @param httpServletRequest an instance of {@link HttpServletRequest} class
     * @param httpServletResponse an instance of {@link HttpServletResponse} class
     * @param filterChain an instance of {@link FilterChain} class
     * @throws ServletException may be thrown during filter executing
     * @throws IOException may be thrown during filter executing
     */
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        httpServletResponse.addHeader("Access-Control-Allow-Credentials", "true");

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
