package org.example.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReturnBookResponse {
    private String borrowerId;
    private String borrowerName;
    private String bookTitle;
    private String bookIsbn;
    private String bookId;
    private boolean isAvailable;
    private LocalDate returnedDate;
}
