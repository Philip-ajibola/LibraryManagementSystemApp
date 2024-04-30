package org.example.dto.request;

import lombok.Data;

@Data
public class GetBookHistoryRequest {
    private String librarianName;
    private String isbn;
}
