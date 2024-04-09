package org.example.dto.request;

import lombok.Data;

@Data
public class RegisterAdminRequest {
    private String username;
    private String password;
}
