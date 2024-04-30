package org.example.dto.response;

import lombok.Data;

import java.time.LocalDate;
@Data
public class BorrowedBookResponse {
    private String authorName;
    private String id;
    private LocalDate maximumDayToReturnBook;
    private String title;
    private String borrowerName;
    private String isbn;
}
