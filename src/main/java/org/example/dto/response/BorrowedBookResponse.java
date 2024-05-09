package org.example.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BorrowedBookResponse {
    private String authorName;
    private String id;
    private LocalDate maximumDayToReturnBook;
    private String title;
    private String borrowerName;
    private String isbn;
}
