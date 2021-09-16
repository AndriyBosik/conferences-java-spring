package com.conferences.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    private UserData userData;
    private String accessToken;
    private String refreshToken;
}
