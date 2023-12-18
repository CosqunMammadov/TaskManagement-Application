package com.example.taskmanagement.security.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthRequest {

    String usernameOrEmail;
    String password;
    String sessionId;
    boolean rememberMe;
}
