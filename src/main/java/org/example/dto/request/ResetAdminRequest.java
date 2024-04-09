package org.example.dto.request;

import lombok.Data;

@Data
public class ResetAdminRequest {
    private String oldUsername;
    private String username;
    private String password;

}

