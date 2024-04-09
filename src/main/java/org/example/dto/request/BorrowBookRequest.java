package org.example.dto.request;

import lombok.Data;

@Data
public class BorrowBookRequest {
    private String isbn;
    private String username;
}
