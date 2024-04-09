package org.example.dto.request;

import lombok.Data;

@Data
public class AddBookRequest {
    private String adminName;
    private String author;
    private String title;
    private String isbn;
}
