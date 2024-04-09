package org.example.dto.request;

import lombok.Data;

@Data
public class ReturnBookRequest {
    private String isbn;
    private String username;
}
