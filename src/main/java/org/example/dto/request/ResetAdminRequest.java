package org.example.dto.request;

import lombok.Data;

@Data
public class ResetAdminRequest {
    private String oldUsername;
    private String newUsername;
    private String password;

}

