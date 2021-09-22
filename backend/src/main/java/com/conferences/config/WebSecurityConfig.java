package com.conferences.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final JwtFilter jwtFilter;

    @Autowired
    public WebSecurityConfig(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService, JwtFilter jwtFilter) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.jwtFilter = jwtFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .cors().disable()
            .httpBasic().disable()
            .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .authorizeRequests()
                    .antMatchers(HttpMethod.GET,
                            "/api/meetings/page/*/*",
                            "/api/meetings/*/topics",
                            "/api/meetings/all",
                            "/api/meetings/*",
                            "/api/proposals/speaker/*",
                            "/api/proposals/moderator/*")
                        .hasAnyAuthority("USER", "SPEAKER", "MODERATOR")
                    .antMatchers(HttpMethod.POST,
                            "/api/files/save-avatar",
                            "/api/users/update-profile")
                        .hasAnyAuthority("USER", "SPEAKER", "MODERATOR")
                    .antMatchers(HttpMethod.GET,
                            "/api/topics/create-from-proposal",
                            "/api/topics/get-by-meeting",
                            "/api/users/speakers",
                            "/api/users/get-topic-available",
                            "/api/users/get-topic-proposed",
                            "/api/users/get-for-meeting",
                            "/api/topic-proposals/count",
                            "/api/topic-proposals")
                        .hasAuthority("MODERATOR")
                    .antMatchers(HttpMethod.POST,
                            "/api/topics/create",
                            "/api/topic-proposals/reject",
                            "/api/meetings/create",
                            "/api/meetings/edit",
                            "/api/users/edit-presence")
                        .hasAuthority("MODERATOR")
                    .antMatchers(HttpMethod.GET,
                            "/api/meetings/speaker/*/*",
                            "/api/topics/get-speaker-proposed-topic-ids",
                            "/api/proposals/speaker/*",
                            "/api/proposals/moderator/*")
                        .hasAuthority("SPEAKER")
                    .antMatchers(HttpMethod.POST,
                            "/api/moderator-proposals/reject",
                            "/api/moderator-proposals/accept",
                            "/api/topic-proposals/create")
                        .hasAuthority("SPEAKER")
                    .antMatchers(HttpMethod.GET, "/api/meetings/check-user-joined")
                        .hasAuthority("USER")
                    .antMatchers(HttpMethod.POST,
                            "/api/users/join-to-meeting")
                        .hasAuthority("USER")
                    .antMatchers(HttpMethod.POST, "/api/auth/login", "/api/sign-up")
                        .anonymous()
                    .antMatchers("/api/images/**")
                        .permitAll()
            .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .formLogin().disable();
    }
}
