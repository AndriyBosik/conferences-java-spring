package com.conferences.model;

import lombok.Builder;
import lombok.Data;

@Data
public class AuthRequest {
    private String login;
    private String password;
}
