package com.conferences.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

public class CorsFilterTest {

    private static CorsFilter filter;
    private static HttpServletRequest request;
    private static HttpServletResponse response;
    private static FilterChain chain;

    private static Map<String, String> headers;

    @BeforeAll
    private static void beforeAll() throws ServletException, IOException {
        filter = new CorsFilter();
        request = Mockito.mock(HttpServletRequest.class);

        response = Mockito.mock(HttpServletResponse.class);
        Mockito
            .doAnswer(invocationOnMock -> headers.put(invocationOnMock.getArgument(0), invocationOnMock.getArgument(1)))
            .when(response).addHeader(any(String.class), any(String.class));

        chain = Mockito.mock(FilterChain.class);
    }

    @BeforeEach
    private void beforeEach() {
        headers = new HashMap<>();
    }

    @Test
    public void shouldSetAccessControlAllowCredentialsToTrue() throws ServletException, IOException {
        filter.doFilterInternal(request, response, chain);

        assertTrue(headers.containsKey("Access-Control-Allow-Credentials"));
        assertEquals(headers.get("Access-Control-Allow-Credentials"), "true");
    }
}
