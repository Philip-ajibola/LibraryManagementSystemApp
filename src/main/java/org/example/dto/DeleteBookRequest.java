package org.example.dto;

import lombok.Data;

@Data
public class DeleteBookRequest {
    private String adminName;
    private String isbn;
}
