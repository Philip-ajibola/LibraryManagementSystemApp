package org.example.dto.response;

import lombok.Data;
import org.example.data.model.BookCategory;

import java.time.LocalDate;

@Data
public class AvailableBookResponse {
    private String id;
    private String author;
    private String isbn;
    private String Title;
    private BookCategory category;
    private LocalDate maximumDateToReturnBook;
}
