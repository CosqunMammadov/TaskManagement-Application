package com.example.taskmanagement.security.data;

import lombok.Getter;

@Getter
public class AuthResponse {
    public AuthResponse(String jwt, String sessionId) {
        this.jwt = jwt;
        this.sessionId = sessionId;
    }

    private final String jwt;
    private final String sessionId;

}
