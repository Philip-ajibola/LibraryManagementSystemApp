package org.example.dto;

import lombok.Data;

@Data
public class RegisterUserRequest {
    private String username;
    private String password;
}
