package org.example.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddBookResponse {
    private String bookId;
    private String bookTitle;
    private String bookISBN;
}
