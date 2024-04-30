package org.example.dto.request;

import lombok.Data;
import org.example.data.model.BookCategory;

@Data
public class AddBookRequest {
    private String adminName;
    private String author;
    private String title;
    private String isbn;
    private BookCategory category;
}
