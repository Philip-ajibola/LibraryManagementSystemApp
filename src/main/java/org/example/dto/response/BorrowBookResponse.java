package org.example.dto.response;

import lombok.Data;
import org.example.data.model.BookCategory;

import java.time.LocalDate;

@Data
public class BorrowBookResponse {
    private String bookId;
    private String bookTitle;
    private String bookAuthor;
    private BookCategory Category;
    private String isbn;
    private LocalDate maximumDateToReturnBook;
}