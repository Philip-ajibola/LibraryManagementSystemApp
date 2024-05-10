package org.example.data.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
@Data
@Document("/transactions")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class History {
    @Id
    private String id;
    private String bookId;
    private String borrowerName;
    private BookStatus bookStatus;
    private LocalDate borrowedDate;
    private LocalDate returnDate;
}
