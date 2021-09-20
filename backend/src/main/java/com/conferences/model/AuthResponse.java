package com.conferences.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    private UserPublicData user;
    private String accessToken;
    private String refreshToken;
}
