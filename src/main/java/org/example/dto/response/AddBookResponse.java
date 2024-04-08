package org.example.dto.response;

import lombok.Data;

@Data
public class AddBookResponse {
    private String bookId;
    private String bookTitle;
    private String bookISBN;
}
