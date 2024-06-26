package org.example.data.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document("books")
public class Book {
    private String title;
    private String authorName;
    private String isbn;
    @Id
    private String id;
    private boolean isAvailable = true;
    private LocalDate maximumDateToReturnBook;
    private String borrowerName;
    private LocalDate BorrowDate;
    private BookCategory bookCategory;


}
