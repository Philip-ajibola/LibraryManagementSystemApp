package org.example.dto.response;

import lombok.Data;

@Data
public class BookResponse {
    private String borrowerId;
    private String borrowerName;
    private String bookTitle;
    private String bookIsbn;
    private String bookId;
}