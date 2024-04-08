package org.example.dto;

import lombok.Data;

@Data
public class ReturnBookRequest {
    private String isbn;
    private String username;
}
