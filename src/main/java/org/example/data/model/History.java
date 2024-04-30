package org.example.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
@Data
@Document("/transactions")
public class History {
    @Id
    private String id;
    private Book book;
    private String borrowerName;
    private BookStatus bookStatus;
    private LocalDate borrowedDate;
    private LocalDate returnDate;
}
