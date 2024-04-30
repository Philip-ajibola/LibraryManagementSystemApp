package org.example.dto.response;

import lombok.Data;

@Data

public class BorrowBookResponseForUser {
    private String bookId;
    private String bookTitle;
    private String bookAuthor;
    private String Category;
}
