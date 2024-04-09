package org.example.dto.request;

import lombok.Data;

@Data
public class DeleteBookRequest {
    private String adminName;
    private String isbn;
}
