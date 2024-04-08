package org.example.dto;

import lombok.Data;

@Data
public class BorrowBookRequest {
    private String isbn;
    private String username;
}
