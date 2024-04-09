package org.example.dto.response;

import lombok.Data;

import java.time.LocalDate;
@Data
public class ReturnBookResponse {
    private String borrowerId;
    private String borrowerName;
    private String bookTitle;
    private String bookIsbn;
    private String bookId;
    private boolean isAvailable;
    private LocalDate returnedDate;
}
